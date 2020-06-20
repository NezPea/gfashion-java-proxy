package com.gfashion.restclient.magento.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartItemExtensionAttributes {

    @SerializedName("discounts")
    private List<MagentoCartDiscount> discounts;
    @SerializedName("negotiable_quote_item")
    private MagentoCartNegotiableQuoteItem negotiableQuoteItem;
}
