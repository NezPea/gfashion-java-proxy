package com.gfashion.restclient.magento.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAdyenPaymentMethod {

    private List<String> brands;
    private List<MagentoCartAdyenPaymentMethodDetail> details;
    private String type;
    private String title;
    private boolean isPaymentMethodOpenInvoiceMethod;
    private String icon;
}
