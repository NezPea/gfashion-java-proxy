package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.gfashion.data.GfSkuEntity;
import com.gfashion.data.repository.dynamodb.GfSkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GfSkuRepositoryImpl implements GfSkuRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfSkuEntity createGfSkuEntity(GfSkuEntity sku) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(sku);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return sku;
    }

    @Override
    public GfSkuEntity readGfSkuEntityById(String skuId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        GfSkuEntity entity = new GfSkuEntity();
        entity.setId(skuId);
        request.addLoad(entity);
        return (GfSkuEntity)dynamoDBMapper.transactionLoad(request).get(0);
    }

    @Override
    public GfSkuEntity updateGfSkuEntity(GfSkuEntity sku) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(sku);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return sku;
    }

    @Override
    /**
     * updated by Candy
     * https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/DynamoDBMapper.CRUDExample1.html
     */
    public void deleteGfSkuEntity (String skuId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(skuId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfSkuEntity sku = GfSkuEntity.builder()
//                .id(skuId)
//                .build();
        GfSkuEntity sku = dynamoDBMapper.load(GfSkuEntity.class, skuId);
        dynamoDBMapper.delete(sku);
    }
}
