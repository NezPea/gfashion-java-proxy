package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductSearchResponse {

    private List<GfProduct> items;
    private GfSearchCriteria search_criteria;
    private Integer total_count;
    private Map<String, Map<String,Object>> avavilable_filters;
}
