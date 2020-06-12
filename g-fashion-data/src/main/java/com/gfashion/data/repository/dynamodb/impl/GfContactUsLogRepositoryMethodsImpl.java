package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gfashion.data.GfContactUsLogEntity;
import com.gfashion.data.repository.dynamodb.GfContactUsLogRepositoryMethods;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/7 19:17
 */
@Repository("gfContactUsLogRepository")
public class GfContactUsLogRepositoryMethodsImpl implements GfContactUsLogRepositoryMethods {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Override
    public GfContactUsLogEntity saveGfContactUsLog(GfContactUsLogEntity gfContactUsLogEntity){
        dynamoDBMapper.save(gfContactUsLogEntity);
        return gfContactUsLogEntity;
    }

    @Override
    public QueryResultPage<GfContactUsLogEntity> getContactUsLog(String contactUsId, String seq, Integer limit, String lastId) {
        if (StringUtils.isEmpty(seq)){
            String partitionKey = contactUsId;
            Map<String, AttributeValue> eav = new HashMap<>();
            eav.put(":val1", new AttributeValue().withS(partitionKey));
            DynamoDBQueryExpression<GfContactUsLogEntity> queryExpression = new DynamoDBQueryExpression<GfContactUsLogEntity>()
                    .withKeyConditionExpression("gfContactUsId = :val1")
                    .withExpressionAttributeValues(eav)
                    .withLimit(limit);

            if (StringUtils.isNotEmpty(lastId)) {//paging
                Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
                exclusiveStartKey.put("gfContactUsId", new AttributeValue().withS(contactUsId));
                exclusiveStartKey.put("seq", new AttributeValue().withS(lastId));
                queryExpression = queryExpression.withExclusiveStartKey(exclusiveStartKey);
            }

            final QueryResultPage<GfContactUsLogEntity> gfContactUsLogtPage = dynamoDBMapper.queryPage(GfContactUsLogEntity.class, queryExpression);
            return gfContactUsLogtPage;
        }else {
            final GfContactUsLogEntity gfContactUsLog = dynamoDBMapper.load(GfContactUsLogEntity.class, contactUsId, seq);
            QueryResultPage<GfContactUsLogEntity> gfContactUsLogtPage= new QueryResultPage<>();
            gfContactUsLogtPage.setResults(Collections.singletonList(gfContactUsLog));
            return gfContactUsLogtPage;
        }

    }


}
