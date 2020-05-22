package com.gfashion.restclient.magento.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MagentoCategoryCustomerAttribute {
    private String attribute_code;
    private String value;
}
