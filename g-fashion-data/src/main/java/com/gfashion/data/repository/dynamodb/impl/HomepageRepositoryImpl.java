package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.gfashion.data.repository.dynamodb.GfHomepageRepository;
import com.gfashion.domain.homepage.CustomizedHomepage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomepageRepositoryImpl implements GfHomepageRepository {
    @Autowired
    DynamoDB dynamoDBM;

    public CustomizedHomepage getDefaultCustomizedHomepage(){
        CustomizedHomepage customizedHomepage = new CustomizedHomepage();
        //TODO: Finish After inserting sample data to DynamoDb
        return customizedHomepage;
    }
}
