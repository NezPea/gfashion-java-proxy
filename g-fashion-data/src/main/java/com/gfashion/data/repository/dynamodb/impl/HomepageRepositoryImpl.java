package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.gfashion.data.entity.GfProductEntity;
import com.gfashion.data.entity.homepage.GfCustomizedHomepageEntity;
import com.gfashion.data.repository.dynamodb.HomepageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class HomepageRepositoryImpl implements HomepageRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public GfCustomizedHomepageEntity getCustomizedHomepageByCustomerId(String customerId){
        return dynamoDBMapper.load(GfCustomizedHomepageEntity.class, customerId);
    }
}
