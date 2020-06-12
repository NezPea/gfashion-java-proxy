package com.gfashion.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gfShipment")
public class GfShipmentEntity {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "orderId")
    private String orderId;

    @DynamoDBAttribute(attributeName = "trackingNumber")
    private String trackingNumber;

    @DynamoDBAttribute(attributeName = "dispatchedAt")
    private String dispatchedAt;
}
