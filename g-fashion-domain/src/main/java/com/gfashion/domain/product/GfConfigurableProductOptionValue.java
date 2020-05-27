package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfConfigurableProductOptionValue {
    private Integer value_index;
    private Object extension_attributes;
    private Object value; // 自定义
}
