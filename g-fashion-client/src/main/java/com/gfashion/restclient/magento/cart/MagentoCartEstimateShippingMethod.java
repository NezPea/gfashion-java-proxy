package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartEstimateShippingMethod {

    @SerializedName("carrier_code")
    private String carrierCode;
    @SerializedName("method_code")
    private String methodCode;
    @SerializedName("carrier_title")
    private String carrierTitle;
    @SerializedName("method_title")
    private String methodTitle;
    private BigDecimal amount;
    @SerializedName("base_amount")
    private BigDecimal baseAmount;
    private Boolean available;
    @SerializedName("error_message")
    private String errorMessage;
    @SerializedName("price_excl_tax")
    private BigDecimal priceExclTax;
    @SerializedName("price_incl_tax")
    private BigDecimal priceInclTax;
}
