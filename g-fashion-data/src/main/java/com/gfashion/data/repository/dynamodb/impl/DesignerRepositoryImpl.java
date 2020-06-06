package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.gfashion.data.GfDesignerEntity;
import com.gfashion.data.repository.dynamodb.GfDesignerRepository;
import com.gfashion.data.repository.dynamodb.exception.SearchAttributeException;
import com.gfashion.domain.designer.GfDesignerSearchAttributeValueMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class DesignerRepositoryImpl implements GfDesignerRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    private Map<String, Class<?>> fieldTypeMapping = Arrays.stream(GfDesignerEntity.class.getDeclaredFields())
            .collect(Collectors.toMap(Field::getName, Field::getType));

    @Override
    public GfDesignerEntity createGfDesignerEntity(GfDesignerEntity Designer) {
        dynamoDBMapper.save(Designer);
        return Designer;
    }

    @Override
    public GfDesignerEntity readGfDesignerEntity(String DesignerId) {
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
    public void deleteGfDesignerEntity(String DesignerId) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(DesignerId)));
        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
        GfDesignerEntity Designer = GfDesignerEntity.builder()
                .id(DesignerId)
                .build();
        dynamoDBMapper.delete(Designer, deleteExpression);
    }

    public List<GfDesignerEntity> searchGfDesignerEntities(GfDesignerSearchAttributeValueMappings attributeValueMappings) {
        // prepare equal conditions
        Map<String, Condition> equalConditions = new ConcurrentHashMap<>();
        attributeValueMappings.getEqualAttributeValueMappings().entrySet().parallelStream().map(entry -> {
            try {
                addConditionForAttribute(entry, equalConditions, ComparisonOperator.EQ);
            } catch (SearchAttributeException e) {
                throw new RuntimeException(e);
            }
            return null;
        }).count();

        // fetch the designers
        DynamoDBScanExpression designerScanExpression = new DynamoDBScanExpression();
        designerScanExpression.setScanFilter(equalConditions);
        return dynamoDBMapper.scan(GfDesignerEntity.class, designerScanExpression);
    }


    private void addConditionForAttribute(Map.Entry<String, String> entry, Map<String, Condition> conditions, ComparisonOperator op) throws SearchAttributeException {
        // valid attribute names
        if (!this.fieldTypeMapping.keySet().contains(entry.getKey())) {
            throw new SearchAttributeException(entry.getKey() + "is not a field of GfDesignerEntity. Please check GfDesignerEntity model on Swagger for details");
        }
        Condition condition = new Condition();
        condition.setComparisonOperator(op);
        List<AttributeValue> valueList = new ArrayList<>();
        valueList.add(getAttributeValue(entry));
        condition.setAttributeValueList(valueList);
        conditions.put(entry.getKey(), condition);
    }

    private AttributeValue getAttributeValue(Map.Entry<String, String> entry) {
        Class<?> fieldType = fieldTypeMapping.get(entry.getKey());
        if (fieldType == Boolean.class || fieldType == Integer.class) {
            return new AttributeValue().withN(entry.getValue());
        } else if (fieldType == String.class) {
            return new AttributeValue().withS(entry.getValue());
        }
        return new AttributeValue().withS(entry.getValue());
    }
}
