package com.gfashion.restclient.magento.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAdyenPaymentInfoAdditionalData {

    @SerializedName("cc_type")
    private String ccType;
    private String number;
    private String cvc;
    private String expiryMonth;
    private String expiryYear;
    private String holderName;
    @SerializedName("store_cc")
    private Boolean storeCc;
    @SerializedName("java_enabled")
    private Boolean javaEnabled;
    @SerializedName("screen_color_depth")
    private Integer screenColorDepth;
    @SerializedName("screen_width")
    private Integer screenWidth;
    @SerializedName("screen_height")
    private Integer screenHeight;
    @SerializedName("timezone_offset")
    private Integer timezoneOffset;
    private String language;
}
