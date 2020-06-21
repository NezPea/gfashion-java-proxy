package com.gfashion.domain.elasticsearch;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GfDesignerSuggestionRequest {
    @NotBlank
    private String keyword;
    private String language = "en";
    private Integer quantity = 10;
}
