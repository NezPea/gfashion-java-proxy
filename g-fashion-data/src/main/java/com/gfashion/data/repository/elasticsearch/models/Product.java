package com.gfashion.data.repository.elasticsearch.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class Product {
    private Long id;
    private String name;
    private long price;
    private String productBrief;
    private String brandName;
    private Set<Designer> designer;
}
