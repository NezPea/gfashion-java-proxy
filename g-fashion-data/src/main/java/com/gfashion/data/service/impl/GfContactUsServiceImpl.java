package com.gfashion.data.service.impl;

import com.gfashion.data.GfContactUsEntity;
import com.gfashion.data.GfContactUsLogEntity;
import com.gfashion.data.GfCustomServiceEntity;
import com.gfashion.data.repository.mysql.GfContactUsLogRepository;
import com.gfashion.data.repository.mysql.GfContactUsRepository;
import com.gfashion.data.repository.mysql.GfCustomServiceRepository;
import com.gfashion.data.service.GfContactUsService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class GfContactUsServiceImpl implements GfContactUsService {

    @Autowired
    private GfContactUsRepository gfContactUsRepository;
    @Autowired
    private GfContactUsLogRepository gfContactUsLogRepository;
    @Autowired
    private GfCustomServiceRepository gfCustomServiceRepository;


    @Override
    public void updateContactStatus(String contactId, String serviceId, Integer status) {
        GfContactUsEntity entity = GfContactUsEntity.builder()
                                .gfContactUsId(contactId)
                                .status(status)
                                .build();
        gfContactUsRepository.save(entity);

    }

    @Override
    public Map<String, String> getContactStatus() {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("1","等待处理");
        statusMap.put("2","客服处理中");
        statusMap.put("3","已解决");
        statusMap.put("4","客户处理中");
        return statusMap;
    }

    @Override
    public Map<String, String> getContactType() {
        Map<String, String> typeMap = new HashMap<>();
        typeMap.put("1","GClub入会升级");
        typeMap.put("2","GCLUB支付咨询");
        typeMap.put("3","GClub会员续期");
        typeMap.put("4","GClub购买咨询 ");
        typeMap.put("5","GFashion购买问题");
        typeMap.put("6","GFashion支付问题");
        typeMap.put("7","GFashion物流与发票");
        typeMap.put("8","GFashion退换货");
        typeMap.put("9","其他类型");
        return typeMap;
    }

    @Override
    public Map<String, Object> siteMailToContact(Map<String, Object> jsonData) {
            int msgType = Integer.parseInt(jsonData.getOrDefault("msgType", "").toString());
            if (3 == msgType) {
                Map<String, Object> temp = cycleToCustom(jsonData);
                temp.remove("lastLog");
                return temp;
            }
            if ( 1 == msgType){ // 判断内容是否为空
                if(!checkJsonData(jsonData)){
                    return new HashMap<>();
                }
                Map<String, Object> temp = firstAndCycleGetContact(jsonData);
                temp.remove("lastLog");
                return temp;
            }
        return new HashMap<>();
    }


    private boolean checkJsonData(Map<String, Object> jsonData){
        String content;
        String picture;
        try { // 判断是否存在内容
            content = jsonData.getOrDefault("content","").toString();
            picture = jsonData.getOrDefault("picture", "").toString();
        }catch (JSONException je){
            System.out.println("not exist param");
            return false;
        }
        return (StringUtils.isNotBlank(content) || StringUtils.isNotBlank(picture));
    }



    //发送消息
    private Map<String, Object> firstAndCycleGetContact(Map<String, Object> jsonData){
        String gfContactUsId = jsonData.getOrDefault("gfContactUsId", "").toString();

        //判断是否是第一次发送
        if(StringUtils.isBlank(gfContactUsId)){
           return customFirstToService(jsonData);
        }
        return customSendMessage(jsonData);
    }


    //第一次调用客服  创建contact  创建contactlog 并且将第一次 的记录返回
    public Map<String, Object> customFirstToService(Map<String, Object> jsonData){

        //创建 contact
        String customerId = jsonData.getOrDefault("gfCustomerId","").toString();
        String language = jsonData.getOrDefault("language","").toString();
        String picture = jsonData.getOrDefault("picture","").toString();
        String content = jsonData.getOrDefault("content","").toString();
        String paramId = jsonData.getOrDefault("paramId","").toString();
        String customName = jsonData.getOrDefault("userName","").toString();
        int pageType = Integer.parseInt(jsonData.getOrDefault("pageType","").toString());

        //构造contact 实体类准备保存
        GfContactUsEntity contactUsEntity = buildContactEntity(customerId, paramId, language, 1, pageType, picture, content);
        contactUsEntity.setCustomName(customName);
        contactUsEntity.setIsRead(1);   //设置为未读
        gfContactUsRepository.save(contactUsEntity);
        //保存后获取id
        String contactId = contactUsEntity.getGfContactUsId();


        //创建log
        GfContactUsLogEntity contactUsLogEntity = buildContactLogEntity(contactId,2, 2,picture,content);
        gfContactUsLogRepository.save(contactUsLogEntity);
        //保存后获取时间戳
        long lastTime  = contactUsLogEntity.getOrderTime();

        //构造返回数据结构
        Map<String, Object> jsonResult = new HashMap<>();
        jsonResult.put("gfCustomerId",customerId);

        //需要将 最新的一条记录保存到 contact 方便查询
        buildResult(jsonResult, contactUsLogEntity);


        buildLogTemp(contactUsEntity);
        gfContactUsRepository.save(contactUsEntity);

        return jsonResult;
    }

    private void buildResult(Map<String, Object> jsonResult, GfContactUsLogEntity contaceUsLogEntity){

        jsonResult.put("isClose",1);
        jsonResult.put("gfContactUsId",contaceUsLogEntity.getGfContactUsId());
        jsonResult.put("createTime",contaceUsLogEntity.getOrderTime());
        List<GfContactUsLogEntity> tempList = new ArrayList<>();
        tempList.add(contaceUsLogEntity);
        jsonResult.put("msgList",tempList);

    }



    private GfContactUsEntity buildContactEntity(String customId, String orderId, String language, int status, int type, String picture, String infoWithExternal){
        GfContactUsEntity contactUsEntity = new GfContactUsEntity();
        contactUsEntity.setCreateTime(System.currentTimeMillis());
        contactUsEntity.setUpdateTime(contactUsEntity.getCreateTime());
        contactUsEntity.setGfCustomerId(customId);
        contactUsEntity.setGfOrderId(orderId);
        contactUsEntity.setLanguage(language);
        contactUsEntity.setStatus(status);
        contactUsEntity.setType(type);
        return contactUsEntity;

    }

    private GfContactUsLogEntity buildContactLogEntity(String gfContactUsId, int status, int infoType, String picture, String infoWithExternal){
        GfContactUsLogEntity contaceUsLogEntity = new GfContactUsLogEntity();
        contaceUsLogEntity.setCreateTime(System.currentTimeMillis());
        contaceUsLogEntity.setOrderTime(System.currentTimeMillis());
        contaceUsLogEntity.setStatus(status);
        contaceUsLogEntity.setPicture(picture);
        contaceUsLogEntity.setInfoType(infoType);
        contaceUsLogEntity.setInfoWithExternal(infoWithExternal);
        contaceUsLogEntity.setGfContactUsId(gfContactUsId);
        gfContactUsLogRepository.save(contaceUsLogEntity);
        return contaceUsLogEntity;

    }



    //用户端轮询获取消息接口
    public Map<String, Object> cycleToCustom(Map<String, Object> jsonData){
        String gfContactUsId = jsonData.getOrDefault("gfContactUsId","").toString();
        String customerId = jsonData.getOrDefault("gfCustomerId","").toString();

        //获取GfContaceUsEntity  判断是否关闭
        GfContactUsEntity contactUsEntity = gfContactUsRepository.getOneById(gfContactUsId);
        //如果没有该字段 则获取全部消息
        String selectTimeStr = jsonData.getOrDefault("createTime", "").toString();
        Long selectTime = null;
        if(StringUtils.isNotBlank(selectTimeStr)){
            selectTime = Long.parseLong(selectTimeStr);
        }

        //从log 表获取新增的 数据  自定义字段获取
        List<Map<String, Object>> resultList = getResultListByParam(gfContactUsId, selectTime);

        Map<String, Object> lastLog =  new HashMap<>();
        getLastTime( selectTime, resultList,  lastLog);
        selectTime =  Long.parseLong(lastLog.get("orderTime").toString());
        contactUsEntity.setCustomFlushTime(selectTime);
        gfContactUsRepository.save(contactUsEntity);

        Map<String, Object> jsonResult = new HashMap<>();
        jsonResult.put("gfCustomerId", customerId);
        jsonResult.put("gfContactUsId", gfContactUsId);
        jsonResult.put("createTime", selectTime);
        jsonResult.put("isClose", contactUsEntity.getStatus() == 3 ? 2 : 1);  //如果等于3 则关闭聊天
        jsonResult.put("msgList", resultList);
        jsonResult.put("lastLog", lastLog);

        return jsonResult;
    }

    //客服端轮询获取消息接口
    public Map<String, Object> cycleToService(Map<String, Object> jsonData){
        String gfContactUsId = jsonData.getOrDefault("gfContactUsId", "").toString();

        String selectTimeStr = jsonData.getOrDefault("createTime","").toString();
        Long selectTime = null;
        if(StringUtils.isNotBlank(selectTimeStr)){
            selectTime = Long.parseLong(selectTimeStr);
        }

        Map<String, Object> jsonResult = new HashMap<>();

        //从log 表获取新增的 数据  自定义字段获取
        List<Map<String, Object>> resultList = getResultListByParam(gfContactUsId, selectTime);
        Map<String, Object> lastLog =  new HashMap<>();
        getLastTime( selectTime, resultList,  lastLog);

        jsonResult.put("gfContactUsId", gfContactUsId);
        jsonResult.put("createTime", selectTime);
        jsonResult.put("msgList", resultList);
        jsonResult.put("lastLog", lastLog);

        return jsonResult;
    }

    //获取最新的时间
    private void getLastTime(Long selectTime, List<Map<String, Object>> resultList, Map<String, Object> lastLog){
        if(null != resultList && !resultList.isEmpty()){
            lastLog.putAll(resultList.get(resultList.size()-1));

        }
    }

    private List<Map<String, Object>> getResultListByParam( String contactId, Long lastTime){
        List<Map<String, Object>> resultList = new ArrayList<>();
        if(null == lastTime){
            resultList =  gfContactUsLogRepository.getByGfContactUsId(contactId);
        }else {
            resultList =  gfContactUsLogRepository.getDistinctByGfContactUsIdAndOrderTime(contactId, lastTime);
        }
        return resultList;
    }


    //用户端发送消息
    public Map<String, Object> customSendMessage(Map<String, Object> jsonData){
        String gfContactUsId = jsonData.getOrDefault("gfContactUsId","").toString();
        String picture = jsonData.getOrDefault("picture","").toString();
        String content = jsonData.getOrDefault("content","").toString();

        GfContactUsEntity contactUsEntity = gfContactUsRepository.getOneById(gfContactUsId);
        //构造log 实体 并且保存
        GfContactUsLogEntity contaceUsLogEntity = buildContactLogEntity(gfContactUsId,2, 2, picture, content);
        gfContactUsLogRepository.save(contaceUsLogEntity);
        //构造返回结果  直接调用用户端轮询 可以获取 用户发送期间 客服发送的消息  ！！！
        Map<String, Object> jsonResult = cycleToCustom(jsonData);

        Map<String, Object> lastLog = (Map<String, Object>)jsonResult.get("lastLog");

        //并且更新刷新的时间 并设置为未读
        updateContactStatus(contactUsEntity, 2, System.currentTimeMillis(),Long.parseLong(jsonResult.getOrDefault("createTime","").toString()), lastLog.toString(), 1);
        //最后一条记录保存到 contact
        buildLogTemp(contactUsEntity);
        gfContactUsRepository.save(contactUsEntity);

        return jsonResult;
    }


    //客服端发送消息
    public Map<String, Object> serviceSendMessage(Map<String, Object> jsonData){
        String gfContactUsId = jsonData.getOrDefault("gfContactUsId","").toString();
        String picture = jsonData.getOrDefault("picture","").toString();
        String content = jsonData.getOrDefault("content","").toString();
        String serviceId = jsonData.getOrDefault("serviceId","").toString();

        int msgType = Integer.parseInt(jsonData.getOrDefault("msgType","0").toString());


        GfContactUsEntity contactUsEntity = gfContactUsRepository.getOneById(gfContactUsId);
        if(StringUtils.isBlank(contactUsEntity.getServiceId())){//如果没有客服 直接保存
            contactUsEntity.setServiceId(serviceId);
        }

        //构造log 实体 并且保存
        GfContactUsLogEntity contactUsLogEntity = buildContactLogEntity(gfContactUsId,2, 1,picture,content);
        gfContactUsLogRepository.save(contactUsLogEntity);

        //直接调用服务端端轮询 可以获取 客服发送期间 用户发送的消息  ！！！
        Map<String, Object> jsonResult = cycleToService(jsonData);
        List<Map<String, Object>> lastLog = (List<Map<String, Object>>)jsonResult.getOrDefault("msgList","");

        //修改各种状态
        updateContactStatus(contactUsEntity, 1 == msgType? 4: 2, System.currentTimeMillis(), null, lastLog.toString(), 2);
        //最后一条记录保存到 contact
        buildLogTemp(contactUsEntity);
        gfContactUsRepository.save(contactUsEntity);
        jsonResult.remove("lastLog");
        return jsonResult;
    }

    private void buildLogTemp(GfContactUsEntity contactUsEntity){
        JSONObject jsonObject = new JSONObject();
        GfContactUsLogEntity contaceUsLogEntity = gfContactUsLogRepository.findFirstByGfContactUsIdEqualsOrderByCreateTimeDesc(contactUsEntity.getGfContactUsId());

        if(2 == contaceUsLogEntity.getInfoType()){ //用户发送给客服
            jsonObject.put("name", contactUsEntity.getCustomName());
        }else {
            GfCustomServiceEntity serviceEntity = gfCustomServiceRepository.getOne(contactUsEntity.getServiceId());
            jsonObject.put("name", serviceEntity.getName());
        }

        jsonObject.put("content", contaceUsLogEntity.getInfoWithExternal());
        jsonObject.put("picture", contaceUsLogEntity.getPicture());
        jsonObject.put("infoType", contaceUsLogEntity.getInfoType());

        contactUsEntity.setContentOfInquiry(jsonObject.toString());
    }


    //发送消息期间改变contact状态
    private void updateContactStatus(GfContactUsEntity contactUsEntity, int status, long updateTime, Long flushTime, String lastLog, Integer isRead){
        if (null == contactUsEntity){ //如果对象为空 则获取该对象
            contactUsEntity = new GfContactUsEntity();
        }
        contactUsEntity.setIsRead(isRead);
        contactUsEntity.setStatus(status);
        contactUsEntity.setUpdateTime(updateTime);
        contactUsEntity.setContentOfInquiry(lastLog);
        if (null != flushTime){
            contactUsEntity.setCustomFlushTime(flushTime);
        }
    }




    //站内信轮询是否有未读消息
    public Map<String, Object> getUnread(String customId){

        //通过id 获取所有的状态不为3的 contact 的记录

        //通过 右连接 或者遍历的方式 获取  log 记录的发布时间 大于 contact 的 flush time的 个数



        return null;
    }

    /**
     * 客服获取 咨询列表   判断type  1 为全部 2 为自己
     */
    @Override
    public List<GfContactUsEntity> getContactList(Map<String,Object> map) {

//        String serviceId, Integer currentNum, Integer type, String language,
//                String designer, String orderId, String customName
        String serviceId = map.getOrDefault("serviceId", "").toString();
        Integer currentNum = Integer.parseInt(map.getOrDefault("currentNum", "").toString());
        int type = Integer.parseInt(map.getOrDefault("type", "0").toString());
        int listType = Integer.parseInt(map.getOrDefault("listType", "0").toString());
        String language = map.getOrDefault("language", "").toString();
        String designer = map.getOrDefault("designer", "").toString();
        String orderId = map.getOrDefault("orderId", "").toString();
        String customName = map.getOrDefault("customName", "").toString();

        List<GfContactUsEntity> resultList = new ArrayList<>();
        if( 1 == listType ){
            resultList = getList(currentNum, null, type, orderId, designer, customName, language);
        }
        if( 2 == listType ){
            resultList = getList( currentNum, serviceId, type, orderId, designer, customName, language);
        }

        return resultList;
    }



    private List<GfContactUsEntity> getList(Integer currentPage, String serviceId, Integer type, String gfOrderId, String brandName, String customName, String language){

        Specification<GfContactUsEntity> specification = new Specification<GfContactUsEntity>() {
            @Override
            public Predicate toPredicate(Root<GfContactUsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                if(StringUtils.isNoneBlank(gfOrderId)){
                    Predicate predicate = criteriaBuilder.like(root.get("gfOrderId").as(String.class), gfOrderId);
                    predicates.add(predicate);
                }
                if(StringUtils.isNoneBlank(brandName)){
                    Predicate predicate = criteriaBuilder.like(root.get("brandName").as(String.class), brandName);
                    predicates.add(predicate);
                }
                if(StringUtils.isNoneBlank(customName)){
                    Predicate predicate = criteriaBuilder.like(root.get("customName").as(String.class), customName);
                    predicates.add(predicate);
                }
                if(StringUtils.isNoneBlank(language)){
                    Predicate predicate = criteriaBuilder.equal(root.get("language").as(String.class), language);
                    predicates.add(predicate);
                }
                if(0 != type){
                    Predicate predicate = criteriaBuilder.equal(root.get("type").as(Integer.class), type);
                    predicates.add(predicate);
                }
                if(StringUtils.isNoneBlank(serviceId)){
                    Predicate predicate = criteriaBuilder.equal(root.get("serviceId").as(String.class), serviceId);
                    predicates.add(predicate);
                }

                if(predicates.size() == 0){
                    return null;
                }
                Predicate[] p = new Predicate[predicates.size()];

                return criteriaBuilder.and(predicates.toArray(p));
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(currentPage, 10, sort);
        Page<GfContactUsEntity> page = gfContactUsRepository.findAll(specification, pageable);
        List<GfContactUsEntity> resultList = page.getContent();
        addServiceName(resultList);
        return resultList;
    }

    //可以优化的地方
    private void addServiceName(List<GfContactUsEntity> resultList){
        if(null == resultList || resultList.size() == 0){
            return;
        }
        //获取所有客服的🆔id
        Set<String> idSet = new HashSet<>();
        for (GfContactUsEntity e:
                resultList) {
            idSet.add(e.getServiceId());
        }
        //通过所有🆔id获取昵称 并保存到map
        List<Map<String, Object>> tempList = gfCustomServiceRepository.getByServiceIdIn(new ArrayList<>(idSet));
        if(null == tempList || tempList.size() == 0){
            return;
        }
        Map<String,String> idMap = new HashMap<>();
        for (Map<String, Object> m:
                tempList) {
            idMap.put(m.get("serviceId").toString(), m.get("name").toString());
        }
        //将昵称写入到数据
        for (GfContactUsEntity e:
                resultList) {
            e.setServiceName(idMap.get(e.getServiceId()));
        }
    }

    @Override
    public void updateReadStatus(String contactUsId) {

        GfContactUsEntity entity = GfContactUsEntity.builder().gfContactUsId(contactUsId).isRead(2).build();
        gfContactUsRepository.save(entity);

    }


}
