package com.gfashion.restclient.magento.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoConfigurableProductOption {
    private Integer id;
    private String attribute_id;
    private String label;
    private Integer position;
    private Boolean is_use_default;
    private List<MagentoConfigurableProductOptionValue> values;
    private Object extension_attributes;
    private Integer product_id;
}
