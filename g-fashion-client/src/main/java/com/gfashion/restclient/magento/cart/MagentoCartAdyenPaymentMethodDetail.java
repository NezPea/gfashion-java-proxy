package com.gfashion.restclient.magento.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAdyenPaymentMethodDetail {

    private String key;
    private Boolean optional;
    private String type;
}
