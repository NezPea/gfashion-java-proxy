package com.gfashion.domain.designer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GfDesignerSearchAttributeValueMappings {
    private Map<String, String> equalAttributeValueMappings;
}
