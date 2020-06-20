package com.gfashion.data.repository.elasticsearch.model;

import com.gfashion.data.repository.elasticsearch.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Constants.INDEX_PRODUCT, type = Constants.TYPE)
public class EsProduct {
    @Id
    private String id;
    private String nameEn;
    private String nameZh;
    private Integer price;
    private Integer discountPrice;
    private String keyword;
    private String desEn;
    private String desZh;
    private String smallPic;
    @Field(type = FieldType.Long, fielddata = true)
    private Long designerId;
    private Long[] categories;
    @Field(type = FieldType.Long, fielddata = true)
    private Long categoryId;
    @Field(type = FieldType.Long)
    private Long topCategoryId;
    @Field(type = FieldType.Integer)
    private Integer sale;
    private String[] sizeList;
}
