package com.gfashion.domain.elasticsearch;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GfDesignerSuggestionResponse {
    private boolean success = false;
    private List<GfDesigner> data;
    private String message;
}
