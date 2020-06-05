package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartItem {

    private Integer item_id;
    private String sku;
    private Integer qty;
    private String name;
    private Integer price;
    private String product_type;

    /**
     * Cart ID
     */
    private Integer quote_id;
    private GfProductOption product_option;

}
