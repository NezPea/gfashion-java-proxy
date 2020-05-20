package com.gfashion.restclient;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.restclient.magento.GfMagentoConverter;
import com.gfashion.restclient.magento.MagentoProduct;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
public class MagentoProductClient {

    @Value("${magento.url.products}")
    private String productsUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    /**
     * getProduct
     * @param sku
     * @return
     */
    public String getProductdetail(String sku) {
        JSONObject productOut = new JSONObject();

        HttpHeaders headers = _restClient.getDefaultHeaders(null);
        ResponseEntity<String> responseEntity = _restClient.exchangeGet(
                productsUrl + sku, String.class, headers);

//            StringBuffer result = new StringBuffer();
//            result.append(responseEntity.getBody());
//            System.out.println("result==="+result.toString());

        JSONObject productObject = new JSONObject(responseEntity.getBody());

        // 获取描述
        String description = "";
        if(productObject.has("custom_attributes")){
            description = getStrFromJsonArray("description",
                    productObject.getJSONArray("custom_attributes"),
                    "attribute_code",
                    "value");
        }

        productOut.put("name", productObject.has("name")?productObject.get("name"):""); // 产品名称
        productOut.put("price", productObject.has("price")?productObject.get("price"):""); // 优惠价
        productOut.put("images", productObject.has("media_gallery_entries")?productObject.getJSONArray("media_gallery_entries"):""); // 媒体图片资料库
        productOut.put("attribute", productObject.has("custom_attributes")?productObject.getJSONArray("custom_attributes"):""); // 产品属性
        productOut.put("description", description); // 描述
        productOut.put("designer_name", ""); // 设计师名　暂时无该信息
        productOut.put("designer_link", ""); // 设计师链接　暂时无该信息
        productOut.put("brand_name", ""); // 品牌名　暂时无该信息
        productOut.put("brand_link", ""); // 品牌链接　暂时无该信息
        productOut.put("purchase_number_limit", ""); // 每人购买限制数量　暂时无该信息

        JSONArray jsonArray = new JSONArray();
        if(productObject.has("product_links")){
            jsonArray = productObject.getJSONArray("product_links"); // 关联产品
            // 循环获取关联产品的名称和图片
            int jsonArrayLength = jsonArray.length();
            for(int i=0;i< jsonArrayLength;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                responseEntity = this._restClient.exchangeGet(
                        productsUrl + jsonObject.getString("linked_product_sku"),
                        String.class, headers);
//                    System.out.println("result111==="+responseEntity.getBody());
                JSONObject rtnObj = new JSONObject(responseEntity.getBody());
                jsonObject.put("name",rtnObj.has("name")?rtnObj.get("name"):""); // 产品名称
                jsonObject.put("price",rtnObj.has("price")?rtnObj.get("price"):""); // 优惠价
                jsonObject.put("image", rtnObj.has("media_gallery_entries")?getStrFromJsonArray("image", rtnObj.getJSONArray("media_gallery_entries"),"media_type","file"):"");
            }
        }
        productOut.put("product_links", jsonArray);  // 相关产品信息

        return productOut.toString();
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

    /**
     * getProductBySku
     * @param skuId
     * @return
     */
    public GfProduct getProductBySku(String skuId){
        ResponseEntity<String> responseEntity = _restClient.exchangeGet(
                productsUrl + skuId,
                String.class,
                null);

        Gson gson = new Gson();
        return _mapper.convertMagentoProductToGfProduct(gson.fromJson(responseEntity.getBody(), MagentoProduct.class));
    }
}