package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gfashion.data.GfSkuCopyEntity;
import com.gfashion.data.repository.dynamodb.GfSkuCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GfSkuCopyRepositoryImpl implements GfSkuCopyRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfSkuCopyEntity createGfSkuCopyEntity(GfSkuCopyEntity sku) {
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
    public GfSkuCopyEntity readGfSkuCopyEntityById(String productId, String skuId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        GfSkuCopyEntity entity = new GfSkuCopyEntity();
        entity.setProductId(productId);
        entity.setSkuId(skuId);
        request.addLoad(entity);
        return (GfSkuCopyEntity)dynamoDBMapper.transactionLoad(request).get(0);
    }


    @Override
    public Map<String, GfSkuCopyEntity> readGfSkuCopyEntityByProductId(String productId){
        TransactionLoadRequest request = new TransactionLoadRequest();


        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(productId));

        DynamoDBQueryExpression<GfSkuCopyEntity> queryExpression = new DynamoDBQueryExpression<GfSkuCopyEntity>()
                .withKeyConditionExpression("productId = :val1").withExpressionAttributeValues(eav);

        List<GfSkuCopyEntity> GfSkuList = dynamoDBMapper.query(GfSkuCopyEntity.class, queryExpression);


        Map<String, GfSkuCopyEntity> gfSkuCopyEntityMap  = new HashMap<>();
        GfSkuList.forEach(sku->{
            GfSkuCopyEntity GfSkuCopyEntity= (GfSkuCopyEntity)sku;
            gfSkuCopyEntityMap.put(GfSkuCopyEntity.getSkuId(),GfSkuCopyEntity);
        });
 
        return gfSkuCopyEntityMap;

    }

    @Override
    public GfSkuCopyEntity updateGfSkuCopyEntity(GfSkuCopyEntity sku) {
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
    public void deleteGfSkuCopyEntity (String productId,String skuId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(skuId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfSkuCopyEntity sku = GfSkuCopyEntity.builder()
//                .id(skuId)
//                .build();
        GfSkuCopyEntity sku = dynamoDBMapper.load(GfSkuCopyEntity.class, productId,skuId);
        dynamoDBMapper.delete(sku);
    }
}
