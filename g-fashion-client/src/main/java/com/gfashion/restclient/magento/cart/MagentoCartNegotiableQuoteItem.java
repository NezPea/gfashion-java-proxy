package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartNegotiableQuoteItem {

    @SerializedName("item_id")
    private Integer itemId;
    @SerializedName("original_price")
    private BigDecimal originalPrice;
    @SerializedName("original_tax_amount")
    private BigDecimal originalTaxAmount;
    @SerializedName("original_discount_amount")
    private BigDecimal originalDiscountAmount;
    @SerializedName("extension_attributes")
    private JSONObject extensionAttributes;
}
