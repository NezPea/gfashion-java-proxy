package com.gfashion.domain.elasticsearch;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class GfProductPage {
    private int pageNo = 1;
    private int pageSize = 20;
    private long total = 0;
    private int totalPage = 0;
    private List<GfProduct> items;
    private Set<GfCategory> categories;
    private Set<GfDesigner> designers;
}
