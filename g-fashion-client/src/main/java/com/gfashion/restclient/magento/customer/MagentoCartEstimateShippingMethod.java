package com.gfashion.restclient.magento.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartEstimateShippingMethod {

    private String carrier_code;
    private String method_code;
    private String carrier_title;
    private String method_title;
    private Integer amount;
    private Integer base_amount;
    private Boolean available;
    private String error_message;
    private Integer price_excl_tax;
    private Integer price_incl_tax;
}
