package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.gfashion.data.GfShipmentEntity;
import com.gfashion.data.repository.dynamodb.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentRepositoryImpl implements ShipmentRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfShipmentEntity get(String id) {
        return dynamoDBMapper.load(GfShipmentEntity.class, id);
    }
}
