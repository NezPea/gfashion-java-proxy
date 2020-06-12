package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.gfashion.data.GfContactUsEntity;
import com.gfashion.data.repository.dynamodb.GfContactUsRepositoryMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/7 19:11
 */
@Repository("gfContactUsRepository")
public class GfContactUsRepositoryMethodsImpl implements GfContactUsRepositoryMethods {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfContactUsEntity saveGfContactUs(GfContactUsEntity gfContactUsEntity) {
        dynamoDBMapper.save(gfContactUsEntity);
        return gfContactUsEntity;
    }

    @Override
    public GfContactUsEntity getContactUsById(String contactUsId) {
        return dynamoDBMapper.load(GfContactUsEntity.class,contactUsId);
    }
}
