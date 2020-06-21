package com.gfashion.magento.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoProductCustomAttribute {
    private String attribute_code;
    private Object value;
}
