package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.gfashion.data.entity.GfProductEntity;
import com.gfashion.data.repository.dynamodb.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public GfProductEntity createGfProductEntity(GfProductEntity product) {
        dynamoDBMapper.save(product);
        return product;
    }

    @Override
    public GfProductEntity readGfProductEntity(String productId) {
        return dynamoDBMapper.load(GfProductEntity.class, productId);
    }

    @Override
    public GfProductEntity updateGfProductEntity(GfProductEntity product) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("productId", new ExpectedAttributeValue(new AttributeValue().withS(product.getProductId())));
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expectedAttributeValueMap);
        dynamoDBMapper.save(product, saveExpression);
        return product;
    }

    @Override
    public void deleteGfProductEntity (String productId) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("productId", new ExpectedAttributeValue(new AttributeValue().withS(productId)));
        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
        GfProductEntity product = GfProductEntity.builder()
                .productId(productId)
                .build();
        dynamoDBMapper.delete(product, deleteExpression);
    }
}
