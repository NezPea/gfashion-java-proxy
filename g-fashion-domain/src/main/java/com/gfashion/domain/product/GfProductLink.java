package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductLink {

    private String sku;
    private String name;
    private BigDecimal price;
    private String file;
    private String link_type;
    private String linked_product_sku;
    private String linked_product_type;
    private Integer position;
    private JSONObject extension_attributes;

}
