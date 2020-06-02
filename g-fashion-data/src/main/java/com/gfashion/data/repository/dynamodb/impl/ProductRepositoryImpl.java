package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.gfashion.data.GfProductEntity;
import com.gfashion.data.repository.dynamodb.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

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
    /**
     * updated by Candy:
     * https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/DynamoDBMapper.OptionalConfig.html
     */
    public GfProductEntity updateGfProductEntity(GfProductEntity product) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(product.getId())));
//        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expectedAttributeValueMap);
//        dynamoDBMapper.save(product, saveExpression);
        dynamoDBMapper.save(product, DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES.config());
        return product;
    }

    @Override
    /**
     * updated by Candy
     * https://docs.aws.amazon.com/zh_cn/amazondynamodb/latest/developerguide/DynamoDBMapper.CRUDExample1.html
     */
    public void deleteGfProductEntity (String productId) {
//        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
//        expectedAttributeValueMap.put(PRODUCT_KEY, new ExpectedAttributeValue(new AttributeValue().withS(productId)));
//        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
//        GfProductEntity product = GfProductEntity.builder()
//                .id(productId)
//                .build();
        GfProductEntity product = dynamoDBMapper.load(GfProductEntity.class, productId);
        dynamoDBMapper.delete(product);
    }
}
