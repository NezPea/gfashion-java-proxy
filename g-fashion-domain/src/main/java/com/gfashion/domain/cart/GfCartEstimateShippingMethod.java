package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartEstimateShippingMethod {

    private String carrierCode;
    private String methodCode;
    private String carrierTitle;
    private String methodTitle;
    private BigDecimal amount;
    private BigDecimal baseAmount;
    private Boolean available;
    private String errorMessage;
    private BigDecimal priceExclTax;
    private BigDecimal priceInclTax;
}
