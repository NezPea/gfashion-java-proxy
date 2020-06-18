package com.gfashion.domain.gfproduct;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCategory {
    //主键
    private String id;

    //三级分类id，对应product表中的categoryId
    private String thirdCategoryId;

    //三级分类名称
    private String thirdCategoryName;

    //二级分类id
    private String secondCategoryId;

    //二级分类名称
    private String secondCategoryName;

    //一级分类id
    private String firstCategoryId;

    //一级分类名称
    private String firstCategoryName;

    //语言
    private String language;

    //简介
    private String brief;
}
