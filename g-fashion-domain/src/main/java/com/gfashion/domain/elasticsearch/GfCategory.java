package com.gfashion.domain.elasticsearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GfCategory {
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "parent_id")
    private Long parentId;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "brief")
    private String brief;
    @JsonProperty(value = "is_active")
    private Boolean isActive;
    private Integer position;
    private Integer level;
    @JsonProperty(value = "product_count")
    private Long productCount;
    @JsonProperty(value = "children_data")
    private Set<GfCategory> childrenData;
}
