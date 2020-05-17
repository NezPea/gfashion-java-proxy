package com.gfashion.restclient;

import com.gfashion.restclient.utils.PropertiesUtils;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String ,Object> ProductOut = new HashMap();
        try{
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
            Gson gson = new Gson();
            JSONObject ProductObject = gson.fromJson(result.toString(), JSONObject.class);
//            JsonObject ProductObject = new JsonParser().parse(result.toString()).getAsJsonObject();

//            String description = getStrFromJsonArray("description",
//                    new Gson().fromJson(ProductObject.getAsString("custom_attributes"),JSONArray.class)
//                    ,"attribute_code",
//                    "value");
            ProductOut.put("name", ProductObject.getAsString("name")); // 产品名称
            ProductOut.put("price", ProductObject.getAsString("price")); // 优惠价
            //JSONArray JsonArray = new Gson().fromJson(ProductObject.getAsString("media_gallery_entries"),JSONArray.class);
            ProductOut.put("images", ProductObject.get("media_gallery_entries"));//new Gson().fromJson(ProductObject.getAsString("media_gallery_entries"),JSONArray.class)); // 媒体图片资料库
//            JSONArray JsonArray = new Gson().fromJson(ProductObject.getAsString("custom_attributes"),JSONArray.class);
            ProductOut.put("attribute", ProductObject.get("custom_attributes"));//new Gson().fromJson(ProductObject.getAsString("custom_attributes"),JSONArray.class)); // 产品属性
            ProductOut.put("description", "");//description
            JSONArray jsonArray = new Gson().fromJson(ProductObject.getAsString("product_links"),JSONArray.class);//ProductObject.getJSONArray("product_links");
//            JSONArray jsonArray = ProductObject.get("product_links");
//            // 循环获取关联产品的名称和图片
//            for(JsonElement jsonElement : jsonArray){
//
//                JsonObject jsonObject = new JsonParser().parse(jsonElement.getAsString()).getAsJsonObject();
//                HttpHeaders headers1= new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON);
//                headers.setBearerAuth(token.toString());
//                ResponseEntity<String> responseEntity1 = rest.exchange(
//                        url.toString()  + "/" + jsonObject.getAsJsonObject("linked_product_sku").toString(),
//                        HttpMethod.GET,
//                        new HttpEntity<Object>(headers1),
//                        String.class);
//
//                JsonObject rtnObj = new JsonParser().parse(responseEntity1.getBody()).getAsJsonObject();
//                jsonObject.add("name",rtnObj.get("name")); // 产品名称
//                jsonObject.add("price",rtnObj.get("price")); // 优惠价
////                jsonObject.add("image", getStrFromJsonArray("image", rtnObj.getJSONArray("media_gallery_entries"),"media_type","file"));
//            }
            ProductOut.put("product_links", jsonArray);  // 相关产品信息
            ProductOut.put("designer_name", ""); // 暂时无该信息
            ProductOut.put("designer_link", ""); // 暂时无该信息
            ProductOut.put("brand_name", ""); // 暂时无该信息
            ProductOut.put("brand_link", ""); // 暂时无该信息
            ProductOut.put("purchase_number_limit", ""); // 暂时无该信息
            System.out.println("============"+result.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSONObject.toJSONString(ProductOut);//result.toString();
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
        HttpEntity<String> request = new HttpEntity<String>(user.toJSONString(), headers);
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
//    public static String getStrFromJsonArray(String key_name, JSONArray JsonArray, String array_key, String array_value){
//        StringBuffer description = new StringBuffer();
//        for(int i=0;i< JsonArray.size();i++){
//            JSONObject jsonObject = JsonArray.getJSONObject(i);
//            if(key_name.equals(jsonObject.getString(array_key))){
//                description.append(jsonObject.getString(array_value));
//                break;
//            }
//        }
//        return description.toString();
//    }
}
