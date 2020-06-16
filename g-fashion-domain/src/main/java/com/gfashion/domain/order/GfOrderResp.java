package com.gfashion.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfOrderResp {
    private List<GfOrderItem> items;
    private Integer total_count;
}
