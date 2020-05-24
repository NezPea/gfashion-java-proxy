package com.gfashion.restclient.magento.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoSearchCriteria {

    private List<MagentoSearchFilterGroup> filter_groups;
    private List<Map<String,String>> sort_orders;
    private Integer page_size;
    private Integer current_page;

}
