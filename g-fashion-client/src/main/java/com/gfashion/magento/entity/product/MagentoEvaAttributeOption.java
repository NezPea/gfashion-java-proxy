package com.gfashion.magento.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoEvaAttributeOption {

    private Integer attribute_id;
    private String attribute_code;
    private String scope;
    private String frontend_input;
    private String file;
    private List<Map<String,String>> options;

}
