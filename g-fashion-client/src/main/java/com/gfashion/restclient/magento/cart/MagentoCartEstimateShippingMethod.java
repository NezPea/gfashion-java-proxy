package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer amount;
    @SerializedName("base_amount")
    private Integer baseAmount;
    private Boolean available;
    @SerializedName("error_message")
    private String errorMessage;
    @SerializedName("price_excl_tax")
    private Integer priceExclTax;
    @SerializedName("price_incl_tax")
    private Integer priceInclTax;
}
