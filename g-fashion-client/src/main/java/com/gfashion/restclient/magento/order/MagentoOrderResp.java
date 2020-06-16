package com.gfashion.restclient.magento.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoOrderResp {
    private List<MagentoOrderItem> items;
    private Integer total_count;
}
