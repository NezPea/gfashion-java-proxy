package com.gfashion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.model.*;
import com.gfashion.data.repository.dynamodb.interfaces.DynamodbRepository;
import com.gfashion.data.repository.dynamodb.typeconverter.DimensionTypeConverter;
import com.gfashion.data.repository.dynamodb.typeconverter.ProductDimensionType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamodbTest {

    @Autowired
    DynamodbRepository dynamodbRepository;

    @Test
    public void createTableTest()throws Exception {
        System.out.println("create table.");

        CreateTableRequest request = new CreateTableRequest()
                .withTableName("DDBOperationLog")
                .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
                .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        dynamodbRepository.createTable(request);
    }

    @Test
    public void getTable() throws Exception{
        String result = dynamodbRepository.getTable("gfproduct");
        Assert.assertEquals(result, "gfproduct");
    }

    /**
     * demo dynamodb custom object put into table using converting class.
     */
    @Test
    public void dynamodbConvertDemo(){
        ProductDimensionType dimType = new ProductDimensionType();
        dimType.setHeight("8.00");
        dimType.setLength("11.0");
        dimType.setThickness("1.0");
    }

    @DynamoDBTable(tableName = "ProductCatalog")
    public static class Book {
        private int id;
        private ProductDimensionType dimensionType;

        @DynamoDBTypeConverted(converter = DimensionTypeConverter.class)
        @DynamoDBAttribute(attributeName = "Dimensions")
        public ProductDimensionType getDimensions() {
            return dimensionType;
        }
    }

}
