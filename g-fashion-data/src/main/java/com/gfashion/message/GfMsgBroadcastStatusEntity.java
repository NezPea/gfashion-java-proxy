package com.gfashion.message;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * This class helps keep track of the status of the broadcast messages.
 * Per our design, the broadcast is shared, this is used to keep the status
 * of broadcast message for each receiver.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gfMsgBroadcastStatus")
public class GfMsgBroadcastStatusEntity {
    @DynamoDBIndexRangeKey(localSecondaryIndexName = "ReceiverMsgIdIndex")
    private String id;

    @DynamoDBHashKey
    private String receiver;

    @DynamoDBRangeKey
    private Long timeSent;

    private Boolean deleted = false;

    private Boolean opened = false;

    private Long timeOpened;

    private Long timeUpdated;

    private Long ttl;
}
