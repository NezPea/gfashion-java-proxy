package com.gfashion.restclient.magento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoProductLinks {
    private String sku;
    private String name;
    private Integer price;
    private String image;
    private String link_type;
    private String linked_product_sku;
    private String linked_product_type;
    private Integer position;
    private JSONObject extension_attributes;
}
