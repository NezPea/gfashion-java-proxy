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
    private String nameEn;
    private String nameZh;
    private String briefEn;
    private String briefZh;
    private String photoUrl;
    private Long topCategoryId;
    private String topCategoryName;
    private Boolean sale;
}