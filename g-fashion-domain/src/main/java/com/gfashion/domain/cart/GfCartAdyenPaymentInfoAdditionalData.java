package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartAdyenPaymentInfoAdditionalData {

    private String ccType;
    private String number;
    private String cvc;
    private String expiryMonth;
    private String expiryYear;
    private String holderName;
    private Boolean storeCc;
    private Boolean javaEnabled;
    private Integer screenColorDepth;
    private Integer screenWidth;
    private Integer screenHeight;
    private Integer timezoneOffset;
    private String language;
}
