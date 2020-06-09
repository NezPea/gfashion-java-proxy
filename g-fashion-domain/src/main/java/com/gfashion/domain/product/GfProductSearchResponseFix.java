package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductSearchResponseFix {

    private List<GfChannelProduct> items;
    private GfSearchCriteria search_criteria;
    private Integer total_count;
    private List<GfAvilableFlter> avavilable_filters;
    private Integer category_id;
    private String category_name;
}
