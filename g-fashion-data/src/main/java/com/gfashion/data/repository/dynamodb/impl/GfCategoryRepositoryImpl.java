package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.gfashion.data.GfCategoryEntity;
import com.gfashion.data.repository.dynamodb.GfCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GfCategoryRepositoryImpl implements GfCategoryRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfCategoryEntity createGfCategoryEntity(GfCategoryEntity category) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(category);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return category;
    }

    @Override
    public GfCategoryEntity readGfCategoryEntityById(String categoryId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        GfCategoryEntity entity = new GfCategoryEntity();
        entity.setId(categoryId);
        request.addLoad(entity);
        return (GfCategoryEntity)dynamoDBMapper.transactionLoad(request).get(0);
    }

    @Override
    public GfCategoryEntity updateGfCategoryEntity(GfCategoryEntity category) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(category);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return category;
    }

    @Override
    /**
     * updated by Candy
     * https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/DynamoDBMapper.CRUDExample1.html
     */
    public void deleteGfCategoryEntity (String categoryId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(categoryId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfCategoryEntity category = GfCategoryEntity.builder()
//                .id(categoryId)
//                .build();
        GfCategoryEntity category = dynamoDBMapper.load(GfCategoryEntity.class, categoryId);
        dynamoDBMapper.delete(category);
    }
}
