package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gfashion.data.repository.dynamodb.interfaces.GfSkuRepository;
import com.gfashion.data.repository.dynamodb.entity.GfSkuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public GfSkuEntity readGfSkuEntityById(String productId, String skuId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        GfSkuEntity entity = new GfSkuEntity();
        entity.setProductId(productId);
        entity.setSkuId(skuId);
        request.addLoad(entity);
        return (GfSkuEntity) dynamoDBMapper.transactionLoad(request).get(0);
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
    public void deleteGfSkuEntity(String productId, String skuId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(skuId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfSkuEntity sku = GfSkuEntity.builder()
//                .id(skuId)
//                .build();
        GfSkuEntity sku = dynamoDBMapper.load(GfSkuEntity.class, productId, skuId);
        dynamoDBMapper.delete(sku);
    }


    @Override
    public List<GfSkuEntity> scanGfSkuEntityByColor(String value) {

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(value));
        ;

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("color = :val1").withExpressionAttributeValues(eav);

        return dynamoDBMapper.scan(GfSkuEntity.class, scanExpression);
    }

    @Override
    public List<GfSkuEntity> loadGfSkuEntity(List<GfSkuEntity> GfSkuEntityList) {

        dynamoDBMapper.batchSave(GfSkuEntityList);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS("black"));
        ;

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("color = :val1").withExpressionAttributeValues(eav);

        return dynamoDBMapper.scan(GfSkuEntity.class, scanExpression);
    }

}
