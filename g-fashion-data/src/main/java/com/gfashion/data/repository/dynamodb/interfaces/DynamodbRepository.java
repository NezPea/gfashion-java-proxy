package com.gfashion.data.repository.dynamodb.interfaces;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;

public interface DynamodbRepository {

    void createTable(CreateTableRequest createTableRequest);

    String getTable(String tablename);

    void listTables();
}
