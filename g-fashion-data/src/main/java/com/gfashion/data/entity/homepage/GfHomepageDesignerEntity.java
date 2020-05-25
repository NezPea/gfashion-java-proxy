package com.gfashion.data.entity.homepage;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class GfHomepageDesignerEntity {
    @DynamoDBAttribute(attributeName = "designerId")
    private Integer designerId;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "cooperatingBrands")
    private List<String> cooperatingBrands;

    @DynamoDBAttribute(attributeName = "country")
    private String country;

    @DynamoDBAttribute(attributeName = "photoUrl")
    private String photoUrl;
}
