package com.gfashion.domain.elasticsearch;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GfProductSearchResponse {
    private boolean success = false;
    private GfProductPage data;
    private List<GfDesigner> designers;
    private List<GfCategory> categories;
}
