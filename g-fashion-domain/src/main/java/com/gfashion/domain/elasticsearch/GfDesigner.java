package com.gfashion.domain.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GfDesigner {
    private Long id;
    private Long designerId;
    private String name;
    private String brief;
    private Integer majorCategoryId;
    private String majorCategoryName;
    private Boolean sale;
}