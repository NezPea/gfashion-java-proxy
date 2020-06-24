package com.gfashion.domain.elasticsearch;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GfProductRecommendationRequest {
    @NotNull
    private Long categoryId;
    @NotNull
    private String productId;
    @NotNull
    private Integer quantity;
    @NotNull
    private String language = "en";
}
