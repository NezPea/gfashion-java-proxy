package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartEstimateShippingMethod {

    private String carrierCode;
    private String methodCode;
    private String carrierTitle;
    private String methodTitle;
    private Integer amount;
    private Integer baseAmount;
    private Boolean available;
    private String errorMessage;
    private Integer priceExclTax;
    private Integer priceInclTax;
}
