package com.gfashion.domain.elasticsearch;

import lombok.Data;

@Data
public class GfProductSearchRequest {
    private String language = "en";
    private String keyword;
    private Long designerId;
    private Long topCategoryId;
    private Long categoryId;
    private Integer sale = 0;
    private String size;
    private String sorting;
    private String order;
    private int pageSize = 20;
    private int pageNo = 1;

    public int getPage() {
        if (pageNo < 1) {
            return 0;
        }
        return pageNo - 1;
    }
}
