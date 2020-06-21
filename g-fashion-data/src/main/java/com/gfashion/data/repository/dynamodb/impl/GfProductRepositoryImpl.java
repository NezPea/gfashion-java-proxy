package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.gfashion.data.repository.dynamodb.Interface.GfProductRepository;
import com.gfashion.data.repository.dynamodb.entity.GfProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GfProductRepositoryImpl implements GfProductRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfProductEntity createGfProductEntity(GfProductEntity product) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(product);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return product;
    }

    @Override
    public GfProductEntity readGfProductEntityById(String productId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        GfProductEntity entity = new GfProductEntity();
        entity.setId(productId);
        request.addLoad(entity);
        return (GfProductEntity)dynamoDBMapper.transactionLoad(request).get(0);
    }

    @Override
    public GfProductEntity updateGfProductEntity(GfProductEntity product) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(product);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return product;
    }

    @Override
    /**
     * updated by Candy
     * https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/DynamoDBMapper.CRUDExample1.html
     */
    public void deleteGfProductEntity (String productId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(productId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfProductEntity product = GfProductEntity.builder()
//                .id(productId)
//                .build();
        GfProductEntity product = dynamoDBMapper.load(GfProductEntity.class, productId);
        dynamoDBMapper.delete(product);
    }
}
