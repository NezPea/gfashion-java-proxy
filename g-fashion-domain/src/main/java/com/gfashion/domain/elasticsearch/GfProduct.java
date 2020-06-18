package com.gfashion.domain.elasticsearch;

import lombok.Data;

@Data
public class GfProduct {
    private String id;
    private String name;
    private Integer price;
    private String brief;
    private String sku;
    private String brandName;
    private String photoUrl;
    private String brandId;
    private String vendorId;
    private Long designerId;
    private String[] categories;
    private String category;
    private String gender;
    private Integer sale;
    private String size;
}
