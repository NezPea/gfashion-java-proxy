package com.gfashion.data.entity.homepage;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class GfHomepageProductEntity {

    @DynamoDBAttribute(attributeName = "productId")
    private Integer productId;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "photoUrl")
    private String photoUrl;

    @DynamoDBAttribute(attributeName = "isFeatured")
    private Boolean isFeatured;
}
