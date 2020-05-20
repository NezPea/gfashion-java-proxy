package com.gfashion.restclient.register.impl;

import com.gfashion.data.CustomerEntity;
import com.gfashion.data.RegisterResponse;
import com.gfashion.data.User;
import com.gfashion.restclient.register.FeginRegisterService;
import com.gfashion.restclient.register.RegisterService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FeginServiceImpl
 * @Description TODO
 * @Author zhangyuanbo
 * @Date 2020/05/18  23:16
 * @Version 1.0
 */
@Slf4j
@Service
public class FeginServiceImpl  implements RegisterService {

    @Autowired
    FeginRegisterService feginRegisterService;

    @Override
    public String getAdminToken() {
        String username = "admin";
        String password = "202064_Hh";
        User adminUser = new User(username, password);
        Gson gson=new Gson();
        String str =  gson.toJson(adminUser);;
        String adminToken= feginRegisterService.getAdminToken(str);
        return adminToken;
    }

    @Override
    public String getAdminToken(User user) {
        Gson gson=new Gson();
        String str =  gson.toJson(user);;
        String adminToken= feginRegisterService.getAdminToken(str);
        return adminToken;
    }

    @Override
    public String getCustomerToken(User user) {
        Gson gson=new Gson();
        String str =  gson.toJson(user);;
        String customerToken= feginRegisterService.getCustomerToken(str);
        return customerToken;
    }

    @Override
    public String registerCustomer( CustomerEntity customer)  {
//
//// -------------------------------> 获取Rest客户端实例
//        RestTemplate restTemplate = new RestTemplate();
//
//        // -------------------------------> 解决(响应数据可能)中文乱码 的问题
//        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
//        converterList.remove(1); // 移除原来的转换器
//        // 设置字符编码为utf-8
//        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
//        converterList.add(1, converter); // 添加新的转换器(注:convert顺序错误会导致失败)
//        restTemplate.setMessageConverters(converterList);
//
//        // -------------------------------> (选择性设置)请求头信息
//        // HttpHeaders实现了MultiValueMap接口
//        HttpHeaders httpHeaders = new HttpHeaders();
//        // 设置contentType
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        // 给请求header中添加一些数据
////        httpHeaders.add("JustryDeng", "这是一个大帅哥!");
//
//        // ------------------------------->将请求头、请求体数据，放入HttpEntity中
//        // 请求体的类型任选即可;只要保证 请求体 的类型与HttpEntity类的泛型保持一致即可
//        // 这里手写了一个json串作为请求体 数据 (实际开发时,可使用fastjson、gson等工具将数据转化为json串)
//        String httpBody = "{\n" +
//                "    \"customer\": {\n" +
//                "        \"email\": \"jdo1e100@example.com\",\n" +
//                "        \"firstname\": \"Jane100\",\n" +
//                "        \"lastname\": \"Doe\",\n" +
//                "        \"addresses\": [\n" +
//                "            {\n" +
//                "                \"defaultShipping\": true,\n" +
//                "                \"defaultBilling\": true,\n" +
//                "                \"firstname\": \"Jane100\",\n" +
//                "                \"lastname\": \"Doe\",\n" +
//                "                \"region\": {\n" +
//                "                    \"regionCode\": \"NY\",\n" +
//                "                    \"region\": \"New York\",\n" +
//                "                    \"regionId\": 43\n" +
//                "                },\n" +
//                "                \"postcode\": \"10756\",\n" +
//                "                \"city\": \"Purchase\",\n" +
//                "                \"telephone\": \"512-555-1111\",\n" +
//                "                \"countryId\": \"US\",\n" +
//                "                \"street\": [\n" +
//                "                    \"123 Oak Ave\"\n" +
//                "                ]\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    },\n" +
//                "    \"password\": \"Password1\"\n" +
//                "}";
//        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, httpHeaders);
//
//        // -------------------------------> URI
//        StringBuffer paramsURL = new StringBuffer("https://www.gfashion2020.tk/index.php/rest/V1/customers");
//        // 字符数据最好encoding一下;这样一来，某些特殊字符才能传过去(如:flag的参数值就是“&”,不encoding的话,传不过去)
//        paramsURL.append("?flag=" + URLEncoder.encode("&", "utf-8"));
//        URI uri = URI.create(paramsURL.toString());
//
//        //  -------------------------------> 执行请求并返回结果
//        // 此处的泛型  对应 响应体数据   类型;即:这里指定响应体的数据装配为String
//        ResponseEntity<String> response =
//                restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//        // -------------------------------> 响应信息
//        //响应码,如:401、302、404、500、200等
//        System.err.println(response.getStatusCodeValue());
//        Gson gson = new Gson();
//        // 响应头
//        System.err.println(gson.toJson(response.getHeaders()));
//        // 响应体
//        if(response.hasBody()) {
//            System.err.println(response.getBody());
//        }

        Gson gson=new Gson();
        String strCustomer=gson.toJson(customer);
        String returnStr=null;
        try {
            returnStr= feginRegisterService.registerCustomer(strCustomer);
            try{
                RegisterResponse registerResponse= gson.fromJson(returnStr, RegisterResponse.class);
                if(registerResponse!=null){
                    returnStr="注册成功";
                }
            }catch (Exception ex){
                returnStr="注册失败";
            }
        }catch(Exception e) {
            returnStr= e.toString();
        }
//        String returnStr=feginRegisterService.registerCustomer(strCustomer);
        return returnStr;
     }

}
