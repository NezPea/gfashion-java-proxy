package com.gfashion.data.repository.elasticsearch;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class ConfigurationInfo {

    @Value("${aws.es.endpoint}")
    private String endpoint;

    @Value("${aws.es.region}")
    private String region;

    @Value("${aws.es.serviceName}")
    private String serviceName;

    @Value("${aws.es.accessKey}")
    private String accessKey;

    @Value("${aws.es.secretkey}")
    private String secretkey;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretkey));
    }
}
