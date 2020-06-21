package com.gfashion.magento.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoSearchFilter {

    private String field;
    private String value;
    private String condition_type;

}
