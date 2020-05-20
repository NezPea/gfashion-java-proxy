package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductLink {

    private String sku;
    private String link_type;
    private String linked_product_sku;
    private String linked_product_type;
    private String position;
}
