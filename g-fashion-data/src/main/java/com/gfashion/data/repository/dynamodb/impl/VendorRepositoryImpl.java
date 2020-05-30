package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.gfashion.data.GfVendorEntity;
import com.gfashion.data.repository.dynamodb.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VendorRepositoryImpl implements VendorRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfVendorEntity createGfVendorEntity(GfVendorEntity Vendor) {
        dynamoDBMapper.save(Vendor);
        return Vendor;
    }

    @Override
    public GfVendorEntity readGfVendorEntity(String VendorId) {
        return dynamoDBMapper.load(GfVendorEntity.class, VendorId);
    }

    @Override
    public GfVendorEntity updateGfVendorEntity(GfVendorEntity Vendor) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("VendorId", new ExpectedAttributeValue(new AttributeValue().withS(Vendor.getVendorId())));
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expectedAttributeValueMap);
        dynamoDBMapper.save(Vendor, saveExpression);
        return Vendor;
    }

    @Override
    public void deleteGfVendorEntity (String VendorId) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("VendorId", new ExpectedAttributeValue(new AttributeValue().withS(VendorId)));
        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
        GfVendorEntity Vendor = GfVendorEntity.builder()
                .vendorId(VendorId)
                .build();
        dynamoDBMapper.delete(Vendor, deleteExpression);
    }
}
