package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfChannelProduct {

    private Integer id;
    private String sku;
    private String name;
//    private Integer attribute_set_id;
    private BigDecimal price;
    private String file;
//    private Integer status;
//    private Integer visibility;
//    private String type_id;
//    private String created_at;
//    private String updated_at;
//    private Integer weight;
    //private List<GfMediaGalleryEntry> media_gallery_entries;  // 媒体图片资源库，包含图片地址
    //private List<GfProductCustomAttribute> custom_attributes; // 自定义属性：可能包含颜色、描述、缩略图、价格
    //private List<GfProductLink> product_links; // 关联产品
    //private GfMediaGalleryEntry extension_attributes; // 扩展属性
    private String designer_name;
    private String designer_link;
    private String brand_name;
    private String brand_link;
 //   private Integer purchase_number_limit; // 每个账号至多购买x件

//    private List<JSONObject> options;
//    private List<JSONObject> tier_prices;
}
