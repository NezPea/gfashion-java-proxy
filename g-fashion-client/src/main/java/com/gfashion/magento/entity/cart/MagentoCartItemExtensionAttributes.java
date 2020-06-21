package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartItemExtensionAttributes {

    @SerializedName("discounts")
    private List<MagentoCartDiscount> discounts;
    @SerializedName("negotiable_quote_item")
    private MagentoCartNegotiableQuoteItem negotiableQuoteItem;
}
