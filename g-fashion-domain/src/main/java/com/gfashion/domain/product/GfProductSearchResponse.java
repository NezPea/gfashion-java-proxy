package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductSearchResponse {

    private List<GfProduct> items;
    private GfSearchCriteria search_criteria;
    private Integer total_count;

}
