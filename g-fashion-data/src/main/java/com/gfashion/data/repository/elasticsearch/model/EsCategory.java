package com.gfashion.data.repository.elasticsearch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EsCategory {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer weight;
}
