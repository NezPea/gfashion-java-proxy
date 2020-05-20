package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfSearchCriteria {

    private List<GfSearchFilterGroup> filter_groups;

}
