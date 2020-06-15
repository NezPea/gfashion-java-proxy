package com.gfashion.domain.elasticsearch;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GfProductSearchResponse {
    private boolean success = false;
    private GfProductPage products;
    private Set<GfDesigner> designers;
    private Set<GfCategory> categories;
    private String message;
}
