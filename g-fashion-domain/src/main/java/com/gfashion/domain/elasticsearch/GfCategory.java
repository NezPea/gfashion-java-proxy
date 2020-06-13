package com.gfashion.domain.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GfCategory {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer weight;
}
