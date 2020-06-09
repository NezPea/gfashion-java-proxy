package com.gfashion.restclient.magento.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartPaymentMethod {

    private String code;
    private String title;
    private String method;
}
