package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.gfashion.data.repository.dynamodb.Interface.GfProductRecRepository;
import com.gfashion.data.repository.dynamodb.entity.GfProductRecEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GfProductRecRepositoryImpl implements GfProductRecRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfProductRecEntity createGfProductRecEntity(GfProductRecEntity productRec) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(productRec);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return productRec;
    }

    @Override
    public GfProductRecEntity readGfProductRecEntityById(String productRecId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        GfProductRecEntity entity = new GfProductRecEntity();
        entity.setId(productRecId);
        request.addLoad(entity);
        return (GfProductRecEntity)dynamoDBMapper.transactionLoad(request).get(0);
    }

    @Override
    public GfProductRecEntity updateGfProductRecEntity(GfProductRecEntity productRec) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(productRec);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return productRec;
    }

    @Override
    /**
     * updated by Candy
     * https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/DynamoDBMapper.CRUDExample1.html
     */
    public void deleteGfProductRecEntity (String productRecId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(productId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfProductRecEntity product = GfProductRecEntity.builder()
//                .id(productId)
//                .build();
        GfProductRecEntity product = dynamoDBMapper.load(GfProductRecEntity.class, productRecId);
        dynamoDBMapper.delete(product);
    }
}
