package com.gfashion.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
@Slf4j
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.accesskey}")
    private String accesskey;

    @Value("${amazon.dynamodb.secretkey}")
    private String secretkey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesskey, secretkey)))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    @Bean
    public DynamoDB dynamoDB(){
        return new DynamoDB(amazonDynamoDB());
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB(), DynamoDBMapperConfig.DEFAULT);
    }

    @PreDestroy
    public void cleanUp() {
        try {
            // Shutting down AWS IdleConnectionReaper thread...
            com.amazonaws.http.IdleConnectionReaper.shutdown();
        } catch (Throwable t) {
            // log error
            log.error("Shutting down IdleConnectionReaper");
        }
    }
}
