package com.gfashion.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gfCategory")
public class GfCategoryEntity {
    //主键
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    //三级分类id，对应product表中的categoryId
    @DynamoDBAttribute(attributeName = "thirdCategoryId")
    private String thirdCategoryId;

    //三级分类名称
    @DynamoDBAttribute(attributeName = "thirdCategoryName")
    private String thirdCategoryName;

    //二级分类id
    @DynamoDBAttribute(attributeName = "secondCategoryId")
    private String secondCategoryId;

    //二级分类名称
    @DynamoDBAttribute(attributeName = "secondCategoryName")
    private String secondCategoryName;

    //一级分类id
    @DynamoDBAttribute(attributeName = "firstCategoryId")
    private String firstCategoryId;

    //一级分类名称
    @DynamoDBAttribute(attributeName = "firstCategoryName")
    private String firstCategoryName;

    //语言
    @DynamoDBAttribute(attributeName = "language")
    private String language;

    //简介
    @DynamoDBAttribute(attributeName = "brief")
    private String brief;
}
