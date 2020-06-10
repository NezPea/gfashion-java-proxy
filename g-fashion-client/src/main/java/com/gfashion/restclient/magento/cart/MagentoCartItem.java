package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartItem {

    @SerializedName("item_id")
    private Integer itemId;
    private String sku;
    private Integer qty;
    private String name;
    private BigDecimal price;
    @SerializedName("product_type")
    private String productType;

    /**
     * Cart ID
     */
    @SerializedName("quote_id")
    private Integer quoteId;
    @SerializedName("product_option")
    private MagentoCartItemProductOption productOption;
    @SerializedName("extension_attributes")
    private MagentoCartItemExtensionAttributes extensionAttributes;
}
