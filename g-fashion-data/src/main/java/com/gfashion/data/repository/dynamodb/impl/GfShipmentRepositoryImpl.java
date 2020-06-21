package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.gfashion.data.repository.dynamodb.interfaces.GfShipmentRepository;
import com.gfashion.data.repository.dynamodb.entity.GfShipmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GfShipmentRepositoryImpl implements GfShipmentRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfShipmentEntity addGfShipmentEntity(GfShipmentEntity shipment) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(shipment);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return shipment;
    }

    @Override
    public GfShipmentEntity readGfShipmentEntityById(String shipmentId) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        GfShipmentEntity entity = new GfShipmentEntity();
        entity.setId(shipmentId);
        request.addLoad(entity);
        return (GfShipmentEntity)dynamoDBMapper.transactionLoad(request).get(0);
    }

    @Override
    public GfShipmentEntity updateGfShipmentEntity(GfShipmentEntity shipment) {
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addPut(shipment);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        dynamoDBMapper.transactionWrite(transactionWriteRequest, config);
        return shipment;
    }

    @Override
    /**
     * updated by Candy
     * https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/DynamoDBMapper.CRUDExample1.html
     */
    public void deleteGfShipmentEntity (String shipmentId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(shipmentId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfShipmentEntity shipment = GfShipmentEntity.builder()
//                .id(shipmentId)
//                .build();
        GfShipmentEntity shipment = dynamoDBMapper.load(GfShipmentEntity.class, shipmentId);
        dynamoDBMapper.delete(shipment);
    }
}
