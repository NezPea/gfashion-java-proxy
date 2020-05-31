package com.gfashion.data.repository.elasticsearch.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductQuery {
    private Long id;
    private String name;
    private long price;
    private String productBrief; //fuzzy search
    private String brandName;
}
