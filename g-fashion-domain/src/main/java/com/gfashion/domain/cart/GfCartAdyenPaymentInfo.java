package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartAdyenPaymentInfo {

    private String method;
    private GfCartAdyenPaymentInfoAdditionalData additional_data;
    private GfCartAddress billingAddress;
}
