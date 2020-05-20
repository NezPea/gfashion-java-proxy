package com.gfashion.restclient;

import com.gfashion.domain.product.GfProduct;
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
        JSONObject ProductOut = new JSONObject();
        try{
            HttpHeaders headers = _restClient.getDefaultHeaders(null);
            ResponseEntity<String> responseEntity = _restClient.exchangeGet(
                    productsUrl + sku, String.class, headers);

//            StringBuffer result = new StringBuffer();
//            result.append(responseEntity.getBody());
//            System.out.println("result==="+result.toString());

            JSONObject ProductObject = new JSONObject(responseEntity.getBody());

            // 获取描述
            String description = "";
            if(ProductObject.has("custom_attributes")){
                description = getStrFromJsonArray("description",
                        ProductObject.getJSONArray("custom_attributes"),
                        "attribute_code",
                        "value");
            }

            ProductOut.put("name", ProductObject.has("name")?ProductObject.get("name"):""); // 产品名称
            ProductOut.put("price", ProductObject.has("price")?ProductObject.get("price"):""); // 优惠价
            ProductOut.put("images", ProductObject.has("media_gallery_entries")?ProductObject.getJSONArray("media_gallery_entries"):""); // 媒体图片资料库
            ProductOut.put("attribute", ProductObject.has("custom_attributes")?ProductObject.getJSONArray("custom_attributes"):""); // 产品属性
            ProductOut.put("description", description); // 描述
            ProductOut.put("designer_name", ""); // 设计师名　暂时无该信息
            ProductOut.put("designer_link", ""); // 设计师链接　暂时无该信息
            ProductOut.put("brand_name", ""); // 品牌名　暂时无该信息
            ProductOut.put("brand_link", ""); // 品牌链接　暂时无该信息
            ProductOut.put("purchase_number_limit", ""); // 每人购买限制数量　暂时无该信息

            JSONArray jsonArray;
            if(ProductObject.has("product_links")){
                jsonArray = ProductObject.getJSONArray("product_links"); // 关联产品
                // 循环获取关联产品的名称和图片
                for(int i=0;i< jsonArray.length();i++){
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
            ProductOut.put("product_links", ProductObject.getJSONArray("product_links"));  // 相关产品信息

        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductOut.toString();
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
