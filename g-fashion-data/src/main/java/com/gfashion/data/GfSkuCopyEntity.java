package com.gfashion.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gfSkuCopy")
public class GfSkuCopyEntity {

    //主键
    @DynamoDBHashKey(attributeName = "productId")
    private String productId;

    //skuId
    @DynamoDBRangeKey(attributeName = "skuId")
    private String skuId;

    //skuName,可以用 productName+sizeName方式
    @DynamoDBAttribute(attributeName = "skuName")
    private String skuName;


    //产品名称
    @DynamoDBAttribute(attributeName = "productName")
    private String productName;

    //语言
    @DynamoDBAttribute(attributeName = "language")
    private String language;

    //原价
    @DynamoDBAttribute(attributeName = "price")
    private BigDecimal price;

    //gClub会员价
    @DynamoDBAttribute(attributeName = "gClubMemberPrice")
    private BigDecimal gClubMemberPrice;

    //二次折扣价
    @DynamoDBAttribute(attributeName = "discountPrice")
    private BigDecimal discountPrice;

    //图片地址
    @DynamoDBAttribute(attributeName = "images")
    private List<String> images;

    //颜色
    @DynamoDBAttribute(attributeName = "color")
    private String color;

    //尺码
    @DynamoDBAttribute(attributeName = "size")
    private String size;

    //库存，这里经常因为补货或者出售实时变化，考虑以后用单独接口实现。
    @DynamoDBAttribute(attributeName = "stockQty")
    private String stockQty;

}
