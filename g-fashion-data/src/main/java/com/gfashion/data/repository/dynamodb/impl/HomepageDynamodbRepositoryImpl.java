package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.gfashion.data.entity.homepage.GfCustomizedHomepageEntity;
import com.gfashion.data.repository.dynamodb.HomepageDynamodbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HomepageDynamodbRepositoryImpl implements HomepageDynamodbRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public GfCustomizedHomepageEntity getCustomizedHomepageByCustomerId(String customerId){
        return dynamoDBMapper.load(GfCustomizedHomepageEntity.class, customerId);
    }
}
