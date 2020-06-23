package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.gfashion.data.repository.dynamodb.interfaces.DynamodbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DynamodbRepositoryImpl implements DynamodbRepository {

    @Autowired
    DynamoDB dynamoDBM;

    @Override
    public void createTable(CreateTableRequest createTableRequest) {
        try {
            dynamoDBM.createTable(createTableRequest);
        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String getTable(String tablename) {
        try {
            return dynamoDBM.getTable(tablename).getTableName();
        } catch (Exception e) {
            System.err.println("Unable to getTable table: ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void listTables() {
        try {
            dynamoDBM.listTables();
        } catch (Exception e) {
            System.err.println("Unable to listTables table: ");
            System.err.println(e.getMessage());
        }
    }
}
