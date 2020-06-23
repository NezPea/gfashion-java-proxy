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
    private String name;
    private Integer price;
    private String keyword;
    private String brief;
    private String sku;
    private String brandName;
    private Integer isFeatured;
    private String photoUrl;
    private String language;
    private String brandId;
    private String vendorId;
    @Field(type = FieldType.Text, fielddata = true)
    private Integer designerId;
    private Integer[] categories;
    private Integer category;
    private String gender;
    private Integer sale;
    private String size;
}
