package com.gfashion.restclient;

import com.gfashion.restclient.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class MagentoClient {

    private RestTemplate rest;

    public MagentoClient(RestTemplate rest) {
        this.rest = rest;
    }

    //
    // GET examples
    //

    /*
     * Specify parameter as varargs argument
     */
    public List<Object> getCountries() {
        return rest.getForObject("https://www.gfashion2020.tk/rest/default/V1/directory/countries", List.class);
    }

    /**
     * getProductdetail
     * @param sku
     * @return
     */
    public String getProductdetail(String sku) {
        StringBuffer result = new StringBuffer();
        StringBuffer token = new StringBuffer();
        StringBuffer url = new StringBuffer();
        JSONObject ProductOut = new JSONObject();
        try{
            // magento产品详细api url
            url.append(PropertiesUtils.getApiConfigYml("api.host") + PropertiesUtils.getApiConfigYml("productdetail.url"));

            token.append(getToken());
            HttpHeaders headers= new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token.toString());

            ResponseEntity<String> responseEntity = rest.exchange(
                    url.toString()  + "/" + sku,
                    HttpMethod.GET,
                    new HttpEntity<Object>(headers),
                    String.class);
            result.append(responseEntity.getBody());

//            System.out.println("result==="+result.toString());
            JSONObject ProductObject = new JSONObject(responseEntity.getBody());

            // 获取描述
            String description = getStrFromJsonArray("description",
                    ProductObject.getJSONArray("custom_attributes"),
                    "attribute_code",
                    "value");
            ProductOut.put("name", ProductObject.get("name")); // 产品名称
            ProductOut.put("price", ProductObject.get("price")); // 优惠价
            ProductOut.put("images", ProductObject.getJSONArray("media_gallery_entries")); // 媒体图片资料库
            ProductOut.put("attribute", ProductObject.getJSONArray("custom_attributes")); // 产品属性
            ProductOut.put("description", description); // 描述
            ProductOut.put("designer_name", ""); // 设计师名　暂时无该信息
            ProductOut.put("designer_link", ""); // 设计师链接　暂时无该信息
            ProductOut.put("brand_name", ""); // 品牌名　暂时无该信息
            ProductOut.put("brand_link", ""); // 品牌链接　暂时无该信息
            ProductOut.put("purchase_number_limit", ""); // 每人购买限制数量　暂时无该信息

            JSONArray jsonArray = ProductObject.getJSONArray("product_links"); // 关联产品
            // 循环获取关联产品的名称和图片
            for(int i=0;i< jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                responseEntity = rest.exchange(
                        url.toString()  + "/" + jsonObject.getString("linked_product_sku"),
                        HttpMethod.GET,
                        new HttpEntity<Object>(headers),
                        String.class);
//                System.out.println("result111==="+responseEntity.getBody());
                JSONObject rtnObj = new JSONObject(responseEntity.getBody());
                jsonObject.put("name",rtnObj.get("name")); // 产品名称
                jsonObject.put("price",rtnObj.get("price")); // 优惠价
                jsonObject.put("image", getStrFromJsonArray("image", rtnObj.getJSONArray("media_gallery_entries"),"media_type","file"));
            }
            ProductOut.put("product_links", ProductObject.getJSONArray("product_links"));  // 相关产品信息


        }catch (Exception e){
            e.printStackTrace();
        }

        return ProductOut.toString();
    }

    /**
     * getToken
     * @return
     */
    public String getToken(){
        StringBuffer token = new StringBuffer();
        StringBuffer url = new StringBuffer();
        url.append(PropertiesUtils.getApiConfigYml("api.host") + PropertiesUtils.getApiConfigYml("token.url"));
        JSONObject user = new JSONObject();
        user.put("username", PropertiesUtils.getApiConfigYml("token.username"));
        user.put("password", PropertiesUtils.getApiConfigYml("token.password"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(user.toString(), headers);
        ResponseEntity<String> responseEntity = rest.postForEntity(url.toString(), request, String.class);

        token.append(responseEntity.getBody().replace("\"", ""));

        return token.toString();
    }

    /**
     * getStrFromJsonArray
     * @param key_name
     * @param JsonArray
     * @return
     */
    public static String getStrFromJsonArray(String key_name, JSONArray JsonArray, String array_key, String array_value){
        StringBuffer description = new StringBuffer();
        for(int i=0;i< JsonArray.length();i++){
            JSONObject jsonObject = JsonArray.getJSONObject(i);
            if(key_name.equals(jsonObject.getString(array_key))){
                description.append(jsonObject.getString(array_value));
                jsonObject.remove(array_key);
                break;
            }
        }
        return description.toString();
    }
}
