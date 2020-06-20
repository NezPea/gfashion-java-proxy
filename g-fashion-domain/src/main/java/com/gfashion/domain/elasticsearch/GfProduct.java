package com.gfashion.domain.elasticsearch;

import lombok.Data;

@Data
public class GfProduct {
    private String id;
    private String nameEn;
    private String nameZh;
    private String price;
    private String discountPrice;
    private String desEn;
    private String desZh;
    private String smallPic;
    private Long designerId;
    private Long categoryId;
    private String designerName;
    private Integer sale;
    private String[] sizeList;
}
