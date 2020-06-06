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

    private Integer itemId;
    private String sku;
    private Integer qty;
    private String name;
    private Integer price;
    private String productType;

    /**
     * Cart ID
     */
    private Integer quoteId;
    private GfCartItemProductOption productOption;
    private GfCartItemExtensionAttributes extensionAttributes;
}
