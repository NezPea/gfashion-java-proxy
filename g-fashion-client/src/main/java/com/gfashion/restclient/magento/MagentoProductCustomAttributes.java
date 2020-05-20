package com.gfashion.restclient.magento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoProductCustomAttributes {
    private String attribute_code;
    private String value;
}
