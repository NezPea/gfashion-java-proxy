package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.gfashion.data.repository.dynamodb.Interface.DDBLogRepository;
import com.gfashion.data.repository.dynamodb.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DDBLogRepositoryImpl implements DDBLogRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public LogEntity createGfLogEntity(LogEntity logEntity) {
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.PUT).build();
        dynamoDBMapper.save(logEntity, config);
        return logEntity;
    }

    @Override
    public LogEntity readGfLogEntityById(String logEntityId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        LogEntity entity = new LogEntity();
        entity.setId(logEntityId);
        request.addLoad(entity);
        return (LogEntity)dynamoDBMapper.transactionLoad(request).get(0);
    }

    @Override
    public LogEntity updateGfLogEntity(LogEntity logEntity) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(logEntity);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return logEntity;
    }

    @Override
    public void deleteGfLogEntity(String logId) {
        LogEntity logEntity = dynamoDBMapper.load(LogEntity.class, logId);
        dynamoDBMapper.delete(logEntity);
    }
}
