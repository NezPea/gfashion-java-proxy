package com.gfashion.domain.elasticsearch;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GfProductSearchResponse {
    private boolean success = true;
    private GfProductPage products;
    private Set<GfDesigner> designers;
    private GfCategory categories;
    private String message;
}
