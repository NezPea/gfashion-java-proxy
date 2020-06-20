package com.gfashion.message.repository.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gfashion.message.GfMsgBroadcastStatusEntity;
import com.gfashion.message.constant.GfMessageConstants;
import com.gfashion.message.repository.GfMsgBroadcastStatusRepository;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GfMsgBroadcastStatusRepositoryImpl implements GfMsgBroadcastStatusRepository {

    @Autowired
    private DynamoDBMapper _dynamoDbMapper;

    @Override
    public void add(GfMsgBroadcastStatusEntity msg) {
        _dynamoDbMapper.save(msg);
    }

    @Override
    public GfMsgBroadcastStatusEntity findById(String receiver, String msgId) {
        Map<String, AttributeValue> queryValues = new HashMap<String, AttributeValue>();
        queryValues.put(":receiver", new AttributeValue().withS(receiver));
        queryValues.put(":msgId", new AttributeValue().withS(msgId));

        DynamoDBQueryExpression<GfMsgBroadcastStatusEntity> queryExpr = new DynamoDBQueryExpression<GfMsgBroadcastStatusEntity>()
                .withIndexName(GfMessageConstants.DYNAMODB_LSI_NAME)
                .withConsistentRead(true)
                .withKeyConditionExpression("receiver = :receiver and id = :msgId")
                .withExpressionAttributeValues(queryValues);

        List<GfMsgBroadcastStatusEntity> messages = _dynamoDbMapper.query(GfMsgBroadcastStatusEntity.class, queryExpr);
        return messages.isEmpty() ? null : messages.get(0);
    }

    @Override
    public List<GfMsgBroadcastStatusEntity> findAll(String receiver, Long secondsAgo, Integer countLimit) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        // range query to get messages for the receiver.
        Instant now = Instant.now();
        Long queryEnd = now.toEpochMilli();
        Long queryStart = queryEnd - (secondsAgo * 1000);

        Map<String, AttributeValue> queryValues = new HashMap<String, AttributeValue>();
        queryValues.put(":receiver", new AttributeValue().withS(receiver));
        queryValues.put(":queryStart", new AttributeValue().withN(String.valueOf(queryStart)));
        queryValues.put(":queryEnd", new AttributeValue().withN(String.valueOf(queryEnd)));

        DynamoDBQueryExpression<GfMsgBroadcastStatusEntity> queryExpr = new DynamoDBQueryExpression<GfMsgBroadcastStatusEntity>()
                .withKeyConditionExpression("receiver = :receiver and timeSent between :queryStart and :queryEnd")
                .withLimit(countLimit)
                .withExpressionAttributeValues(queryValues);

        QueryResultPage<GfMsgBroadcastStatusEntity> result = _dynamoDbMapper.queryPage(GfMsgBroadcastStatusEntity.class, queryExpr);

        List<GfMsgBroadcastStatusEntity> messages = result.getResults();

        return messages;
    }
}
