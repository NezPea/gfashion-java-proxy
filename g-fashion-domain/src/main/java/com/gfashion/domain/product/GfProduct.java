package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProduct {

    private Integer id;
    private String sku;
    private String name;
    private Integer attribute_set_id;
    private double price;
    private Integer status;
    private Integer visibility;
    private String type_id;
    private String created_at;
    private String updated_at;
    private Integer weight;
    private JSONObject extension_attributes;
    private List<GfProductLink> product_links;
    private List<JSONObject> options;
    private List<GfMediaGalleryEntry> media_gallery_entries;
    private List<JSONObject> tier_prices;
    private List<JSONObject> custom_attributes;

}
