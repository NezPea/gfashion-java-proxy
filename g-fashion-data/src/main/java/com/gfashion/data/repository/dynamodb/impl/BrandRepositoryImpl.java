package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.gfashion.data.repository.dynamodb.Interface.GfBrandRepository;
import com.gfashion.data.repository.dynamodb.entity.GfBrandEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BrandRepositoryImpl implements GfBrandRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfBrandEntity createGfBrandEntity(GfBrandEntity Brand) {
        dynamoDBMapper.save(Brand);
        return Brand;
    }

    @Override
    public GfBrandEntity readGfBrandEntity(String BrandId) {
        return dynamoDBMapper.load(GfBrandEntity.class, BrandId);
    }

    @Override
    public GfBrandEntity updateGfBrandEntity(GfBrandEntity Brand) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(Brand.getId())));
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expectedAttributeValueMap);
        dynamoDBMapper.save(Brand, saveExpression);
        return Brand;
    }

    @Override
    public void deleteGfBrandEntity (String BrandId) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(BrandId)));
        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
        GfBrandEntity Brand = GfBrandEntity.builder()
                .id(BrandId)
                .build();
        dynamoDBMapper.delete(Brand, deleteExpression);
    }
}
