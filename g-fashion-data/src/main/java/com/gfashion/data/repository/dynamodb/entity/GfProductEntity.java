package com.gfashion.data.repository.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gfProduct")
public class GfProductEntity {
    //主键产品id,也可叫做spuID,或者父SkuId，这里1个productId对应多个skuID
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    //英文名称
    @DynamoDBAttribute(attributeName = "nameEn")
    private String nameEn;

    //产品名称
    @DynamoDBAttribute(attributeName = "nameZh")
    private String nameZh;

    //产品条码
    @DynamoDBAttribute(attributeName = "productCode")
    private String productCode;

    //原价
    @DynamoDBAttribute(attributeName = "price")
    private Integer price;

    //二次折扣价
    @DynamoDBAttribute(attributeName = "discountPrice")
    private Integer discountPrice;

    //币种
    @DynamoDBAttribute(attributeName = "currency")
    private String currency;

    //是否可以二次打折
    @DynamoDBAttribute(attributeName = "ifSecondDiscount")
    private boolean isSecondDiscount;

    //语言
    @DynamoDBAttribute(attributeName = "keyword")
    private String keyword;

    //编辑笔记
    @DynamoDBAttribute(attributeName = "desEn")
    private String desEn;

    //编辑笔记
    @DynamoDBAttribute(attributeName = "desZh")
    private String desZh;

    //尺码信息
    @DynamoDBAttribute(attributeName = "specificationEn")
    private String specificationEn;

    //尺码信息
    @DynamoDBAttribute(attributeName = "specificationZh")
    private String specificationZh;

    //细节与养护
    @DynamoDBAttribute(attributeName = "conservationEn")
    private String conservationEn;

    //细节与养护
    @DynamoDBAttribute(attributeName = "conservationZh")
    private String conservationZh;

    //售后
    @DynamoDBAttribute(attributeName = "deliveryReturnEn")
    private String deliveryReturnEn;

    //售后
    @DynamoDBAttribute(attributeName = "deliveryReturnZh")
    private String deliveryReturnZh;

    //设计师id
    @DynamoDBAttribute(attributeName = "designerId")
    private Integer designerId;

    //设计师名称
    @DynamoDBAttribute(attributeName = "designerName")
    private String designerName;

    //设计师链接
    @DynamoDBAttribute(attributeName = "designerLink")
    private String designerLink;


    @DynamoDBAttribute(attributeName = "photoUrl")
    private List<String> photoUrl;


    @DynamoDBAttribute(attributeName = "smallPic")
    private String smallPic;

    @DynamoDBAttribute(attributeName = "categoryId")
    private Integer categoryId;

    @DynamoDBAttribute(attributeName = "categories")
    private List<String> categories;

    //大类
    @DynamoDBAttribute(attributeName = "topCategoryId")
    private Integer topCategoryId;

    //促销
    @DynamoDBAttribute(attributeName = "sale")
    private boolean sale;

    @DynamoDBAttribute(attributeName = "purchaseNumber")
    private Integer purchaseNumber;

    //限购数量 是否可见
    @DynamoDBAttribute(attributeName = "showLimit")
    private boolean showLimit;

    //大类

    @DynamoDBAttribute(attributeName = "sku")
    private Map<String,GfSkuEntity> sku;

    @DynamoDBAttribute(attributeName = "sizeList")
    private List<String> sizeList;

}
