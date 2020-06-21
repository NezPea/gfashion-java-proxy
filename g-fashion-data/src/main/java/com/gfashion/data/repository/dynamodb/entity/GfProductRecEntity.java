package com.gfashion.data.repository.dynamodb.entity;

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
@DynamoDBTable(tableName = "gfProductRec")
//产品推荐表
public class GfProductRecEntity {

    //主键
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    //产品id,也可叫做spuID,或者父SkuId，这里1个productId对应多个skuID
    @DynamoDBAttribute(attributeName = "productId")
    private String productId;

    //相同designer推荐列表，里边是skuID
    @DynamoDBAttribute(attributeName = "designerRecProductList")
    private List<String> designerRecProductList;

    //相同category推荐列表，里边是skuID
    @DynamoDBAttribute(attributeName = "categoryRecProductList")
    private List<String> categoryRecProductList;

    //推荐搭配列表，里边是skuID
    @DynamoDBAttribute(attributeName = "styledWithProductList")
    private List<String> styledWithProductList;


}
