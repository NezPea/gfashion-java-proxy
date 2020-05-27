package com.gfashion.restclient.magento.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoConfigurableProductOptionValue {
    private Integer value_index;
    private Object extension_attributes;
    private Object value; // 自定义
}
