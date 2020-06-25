package com.gfashion.message.repository.impl;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gfashion.message.GfMsgMessageEntity;
import com.gfashion.message.constant.GfMessageConstants;
import com.gfashion.message.repository.GfMsgMessageRepository;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GfMsgMessageRepositoryImpl implements GfMsgMessageRepository {

  @Autowired
  private DynamoDBMapper _dynamoDbMapper;

  @Override
  public void add(GfMsgMessageEntity t) {
    _dynamoDbMapper.save(t);
  }

  @Override
  public GfMsgMessageEntity findById(String receiver, String msgId) {
    Map<String, AttributeValue> queryValues = new HashMap<String, AttributeValue>();
    queryValues.put(":receiver", new AttributeValue().withS(receiver));
    queryValues.put(":msgId", new AttributeValue().withS(msgId));

    DynamoDBQueryExpression<GfMsgMessageEntity> queryExpr = new DynamoDBQueryExpression<GfMsgMessageEntity>()
            .withIndexName(GfMessageConstants.DYNAMODB_LSI_NAME)
            .withConsistentRead(true)
            .withKeyConditionExpression("receiver = :receiver and id = :msgId")
            .withExpressionAttributeValues(queryValues);

    List<GfMsgMessageEntity> messages = _dynamoDbMapper.query(GfMsgMessageEntity.class, queryExpr);
    return messages.isEmpty() ? null : messages.get(0);
  }

  @Override
  public List<GfMsgMessageEntity> findAll(String receiver, Long secondsAgo, Integer countLimit) {
    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

    // range query to get messages for the receiver.
    Instant now = Instant.now();
    Long queryEnd = now.toEpochMilli();
    Long queryStart = queryEnd - (secondsAgo * 1000);

    Map<String, AttributeValue> queryValues = new HashMap<String, AttributeValue>();
    queryValues.put(":receiver", new AttributeValue().withS(receiver));
    queryValues.put(":queryStart", new AttributeValue().withN(String.valueOf(queryStart)));
    queryValues.put(":queryEnd", new AttributeValue().withN(String.valueOf(queryEnd)));

    DynamoDBQueryExpression<GfMsgMessageEntity> queryExpr = new DynamoDBQueryExpression<GfMsgMessageEntity>()
            .withKeyConditionExpression("receiver = :receiver and timeSent between :queryStart and :queryEnd")
            .withLimit(countLimit)
            .withExpressionAttributeValues(queryValues);

    queryExpr.setScanIndexForward(false);

    // use queryPage for limiting the number of results.
    QueryResultPage<GfMsgMessageEntity> result = _dynamoDbMapper.queryPage(GfMsgMessageEntity.class, queryExpr);

    List<GfMsgMessageEntity> messages = result.getResults();

    //
    // get the broadcast messages.
    //
    queryValues.put(":receiver", new AttributeValue().withS(GfMessageConstants.BROADCAST_RECEIVER));
    // List<GfMsgMessageEntity> broadcastMessages = _dynamoDbMapper.query(GfMsgMessageEntity.class, queryExpr);
    result = _dynamoDbMapper.queryPage(GfMsgMessageEntity.class, queryExpr);
    List<GfMsgMessageEntity> broadcastMessages = result.getResults();

    List<GfMsgMessageEntity> allMessages = ListUtils.union(messages, broadcastMessages);

    // order by time sent desc.
    Collections.sort(allMessages, (m1, m2) -> {
      return (int) (m2.getTimeUpdated() - m1.getTimeUpdated());
    });

    return allMessages;
  }

  @Override
  public void receiverDelete(String receiver, String msgId) {
    GfMsgMessageEntity e = findById(receiver, msgId);
    if (null == e) {
      throw new AmazonServiceException(GfMessageConstants.MESSAGE_NOT_EXIST_ERROR);
    }
    if (GfMessageConstants.BROADCAST_RECEIVER == e.getReceiver()) {
      throw new AmazonServiceException("Can't delete broadcast message.");
    }
    _dynamoDbMapper.delete(e);
  }

  @Override
  public void senderDelete(String sender, String msgId) {
    // TODO: implement unsend by sender.
    return ;
  }
}