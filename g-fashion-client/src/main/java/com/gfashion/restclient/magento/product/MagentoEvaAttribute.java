package com.gfashion.restclient.magento.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoEvaAttribute {
    private List<MagentoEvaAttributeOption> items;
    private MagentoSearchCriteria search_criteria;
    private Integer total_count;
}
