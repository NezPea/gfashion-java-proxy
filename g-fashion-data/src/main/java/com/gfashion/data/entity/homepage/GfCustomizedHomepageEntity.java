package com.gfashion.data.entity.homepage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gf-customized-homepage")
public class GfCustomizedHomepageEntity {

    @DynamoDBHashKey(attributeName = "customerId")
    @DynamoDBAutoGeneratedKey
    private Integer customerId;

    @DynamoDBAttribute(attributeName = "recommendedProducts")
    private List<GfHomepageProductEntity> recommendedProducts;

    @DynamoDBAttribute(attributeName = "recommendedDesigners")
    private List<GfHomepageDesignerEntity> recommendedDesigners;

    @DynamoDBAttribute(attributeName = "recommendedBrands")
    private List<GfHomepageBrandEntity> recommendedBrands;

    @DynamoDBAttribute(attributeName = "followingDesigners")
    private List<GfHomepageDesignerEntity> followingDesigners;

    @DynamoDBAttribute(attributeName = "followingBrands")
    private List<GfHomepageBrandEntity> followingBrands;

}
