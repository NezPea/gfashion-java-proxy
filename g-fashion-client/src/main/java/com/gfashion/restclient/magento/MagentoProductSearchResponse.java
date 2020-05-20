package com.gfashion.restclient.magento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoProductSearchResponse {

    private List<MagentoProduct> items;
    private MagentoSearchCriteria search_criteria;
    private Integer total_count;

}
