package com.gfashion.domain.elasticsearch;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GfProductRecommendationResponse {
    private boolean success = true;
    private List<GfProduct> sameCategoryProducts;
    private List<GfProduct> differentCategoryProducts;
}
