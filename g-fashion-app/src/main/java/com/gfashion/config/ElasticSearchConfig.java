package com.gfashion.config;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.gfashion.data.repository.elasticsearch.interceptor.AWSRequestSigningApacheInterceptor;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


@Configuration
public class ElasticSearchConfig {
    @Value("${aws.es.endpoint}")
    private String endpoint;

    @Value("${aws.es.region}")
    private String region;

    @Value("${aws.es.serviceName}")
    private String serviceName;

    @Value("${aws.es.accessKey}")
    private String accessKey;

    @Value("${aws.es.secretKey}")
    private String secretKey;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }

//    @Bean
//    public RestClient restClient(AWSCredentialsProvider credentialsProvider) {
//        AWS4Signer signer = new AWS4Signer();
//        signer.setServiceName(serviceName);
//        signer.setRegionName(region);
//        HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(serviceName, signer, credentialsProvider);
//        return RestClient.builder(HttpHost.create(endpoint)).setHttpClientConfigCallback(hacb -> hacb.addInterceptorLast(interceptor)).build();
//    }

    @Bean
    public RestHighLevelClient awsClient(AWSCredentialsProvider credentialsProvider) {
        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName(serviceName);
        signer.setRegionName(region);
        HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(serviceName, signer, credentialsProvider);
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(endpoint)).setHttpClientConfigCallback(hacb -> hacb.addInterceptorLast(interceptor)));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(AWSCredentialsProvider credentialsProvider) {
        return new ElasticsearchRestTemplate(awsClient(credentialsProvider));
    }

}
