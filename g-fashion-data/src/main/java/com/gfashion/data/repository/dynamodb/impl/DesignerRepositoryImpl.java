package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.gfashion.data.GfDesignerEntity;
import com.gfashion.data.repository.dynamodb.GfDesignerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DesignerRepositoryImpl implements GfDesignerRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfDesignerEntity createGfDesignerEntity(GfDesignerEntity Designer) {
        dynamoDBMapper.save(Designer);
        return Designer;
    }

    @Override
    public GfDesignerEntity readGfDesignerEntity(String DesignerId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS("USA"));
//        eav.put(":val2", new AttributeValue().withS(country));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("country = :val1").withExpressionAttributeValues(eav);

        List<GfDesignerEntity> scanResult = dynamoDBMapper.parallelScan(GfDesignerEntity.class, scanExpression,16);
        for (GfDesignerEntity bicycle : scanResult) {
            System.out.println(bicycle);
        }
        return dynamoDBMapper.load(GfDesignerEntity.class, DesignerId);
    }

    @Override
    public GfDesignerEntity updateGfDesignerEntity(GfDesignerEntity Designer) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(Designer.getId())));
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expectedAttributeValueMap);
        dynamoDBMapper.save(Designer, saveExpression);
        return Designer;
    }

    @Override
    public void deleteGfDesignerEntity (String DesignerId) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(DesignerId)));
        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
        GfDesignerEntity Designer = GfDesignerEntity.builder()
                .id(DesignerId)
                .build();
        dynamoDBMapper.delete(Designer, deleteExpression);
    }

}
