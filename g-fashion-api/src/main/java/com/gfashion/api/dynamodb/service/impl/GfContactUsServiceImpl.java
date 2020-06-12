package com.gfashion.api.dynamodb.service.impl;

import com.gfashion.api.dynamodb.service.GfContactUsService;
import com.gfashion.data.GfContactUsEntity;
import com.gfashion.data.repository.dynamodb.GfContactUsRepositoryMethods;
import com.gfashion.data.repository.dynamodb.constant.Constant;
import com.gfashion.data.repository.dynamodb.key.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/8 21:43
 */
@Service
public class GfContactUsServiceImpl implements GfContactUsService {

    @Autowired
    private GfContactUsRepositoryMethods gfContactUsRepository;

    @Autowired
    private SequenceService sequenceService;

    @Override
    public GfContactUsEntity saveGfContactUs(GfContactUsEntity gfContactUsEntity) {
        //TODO 国家城市参数接收
        final String contactUsId = sequenceService.getGfContactUsId(LocalDateTime.now(), "01", "01");
        gfContactUsEntity.setGfContactUsId(contactUsId);

        StringBuffer stringBuffer = new StringBuffer();
        //TODO 生成规则：生成规则 GFASHION/GCLUB-咨询种类-用户名称-订单号（订单相关咨询）
        final String title = stringBuffer.append("GFASHION")
                .append(gfContactUsEntity.getType())
                .append(gfContactUsEntity.getGfCustomerId())
                .append(gfContactUsEntity.getGfOrderId()).toString();
        gfContactUsEntity.setTitle(title);

        gfContactUsEntity.setCreateTime(Constant.yyyyMMddHHmmssDf.format(LocalDateTime.now()));

        final GfContactUsEntity saved = gfContactUsRepository.saveGfContactUs(gfContactUsEntity);
        return saved;
    }

    @Override
    public GfContactUsEntity getContactUs(String contactUsId) {

        final GfContactUsEntity contactUsById = gfContactUsRepository.getContactUsById(contactUsId);
        return contactUsById;
    }

    @Override
    public GfContactUsEntity updateContactUs(GfContactUsEntity gfContactUsEntity) {

        gfContactUsEntity.setUpdateTime(Constant.yyyyMMddHHmmssDf.format(LocalDateTime.now()));
        final GfContactUsEntity saved = gfContactUsRepository.saveGfContactUs(gfContactUsEntity);
        return saved;
    }
}
