package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartAdyenPaymentMethod {

    private List<String> brands;
    private List<GfCartAdyenPaymentMethodDetail> details;
    private String type;
    private String title;
    private boolean isPaymentMethodOpenInvoiceMethod;
    private String icon;
}
