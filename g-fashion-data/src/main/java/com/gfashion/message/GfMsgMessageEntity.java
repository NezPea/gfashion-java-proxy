package com.gfashion.message;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gfMsgMessage")
public class GfMsgMessageEntity {
  @DynamoDBIndexRangeKey(localSecondaryIndexName = "ReceiverMsgIdIndex")
  private String id;

  @DynamoDBHashKey
  private String receiver;

  @DynamoDBRangeKey
  private Long timeSent;

  private String sender;

  private HashMap<String, String> title;

  private HashMap<String, String> content;

  private String picture;

  private String category;

  private Long timeUpdated;

  private String updatedBy;

  private Boolean opened;

  private Long timeOpened;

  private String contract;

  private String data;
}