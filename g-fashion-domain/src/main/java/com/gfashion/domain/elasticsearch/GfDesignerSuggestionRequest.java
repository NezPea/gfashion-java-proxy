package com.gfashion.domain.elasticsearch;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GfDesignerSuggestionRequest {
    @NotBlank
    private String keyword;
}
