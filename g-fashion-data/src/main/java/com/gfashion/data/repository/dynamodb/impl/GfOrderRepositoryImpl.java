package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.gfashion.data.repository.dynamodb.Interface.GfOrderRepository;
import com.gfashion.data.repository.dynamodb.entity.GfOrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GfOrderRepositoryImpl implements GfOrderRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfOrderEntity addGfOrderEntity(GfOrderEntity order) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(order);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return order;
    }

    @Override
    public GfOrderEntity readGfOrderEntityById(String orderId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        GfOrderEntity entity = new GfOrderEntity();
        entity.setId(orderId);
        request.addLoad(entity);
        return (GfOrderEntity)dynamoDBMapper.transactionLoad(request).get(0);
    }

    @Override
    public GfOrderEntity updateGfOrderEntity(GfOrderEntity order) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(order);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return order;
    }

    @Override
    /**
     * updated by Candy
     * https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/DynamoDBMapper.CRUDExample1.html
     */
    public void deleteGfOrderEntity (String orderId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(orderId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfOrderEntity order = GfOrderEntity.builder()
//                .id(orderId)
//                .build();
        GfOrderEntity order = dynamoDBMapper.load(GfOrderEntity.class, orderId);
        dynamoDBMapper.delete(order);
    }
}
