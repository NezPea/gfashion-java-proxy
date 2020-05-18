package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProduct {
    private Integer id;
    private String sku;
    private String name;
    private Integer price;
    private List <JSONObject> media_gallery_entries; // 媒体图片资源库，包含图片地址
    private List <JSONObject> custom_attributes; // 自定义属性：可能包含颜色、描述、缩略图、价格
    private List <JSONObject> product_links; // 关联产品
    private JSONObject extension_attributes; // 扩展属性
    private String designer_name;
    private String designer_link;
    private String brand_name;
    private String brand_link;
    private Integer purchase_number_limit; // 每个账号至多购买x件
}
