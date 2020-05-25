package com.gfashion.data.entity.homepage;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class GfHomepageBrandEntity {
    @DynamoDBAttribute(attributeName = "brandId")
    private Integer brandId;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "country")
    private String country;

    @DynamoDBAttribute(attributeName = "photoUrl")
    private String photoUrl;

    @DynamoDBAttribute(attributeName = "link")
    private String link;
}
