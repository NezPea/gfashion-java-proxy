package com.gfashion.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class MagentoClientAdvanced {

    @Autowired
    private RestTemplate restTemplate;

    private String token;

    /**
     * 临时代码，将来使用全局配置
     * TODO:改为使用项目全局配置
     */
    private static final String ADMIN_TOKEN = "0gai3kjv7996vykntyxujpvmpabauawp";
    private static final String SERVER_DOMAIN = "http://192.168.0.101/";

    HttpHeaders httpHeaders = new HttpHeaders();

    public MagentoClientAdvanced() {
        super();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(ADMIN_TOKEN);
    }

    public void setToken(String token) {
        this.token = token;
        httpHeaders.setBearerAuth(token);
    }

    public String getToken() {
        return this.token;
    }

    /**
     * 请求 magento api，返回响应体 字符串
     *
     * @param url
     * @param uriVariables
     * @return
     * @throws RestClientException
     */
    public String getForBody(String url, Map<String, String> uriVariables) throws RestClientException {
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(SERVER_DOMAIN + url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class, uriVariables);
        return responseEntity.getBody();
    }

    public String getForBody(String url) throws RestClientException {
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(SERVER_DOMAIN + url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        return responseEntity.getBody();
    }

    /**
     * 请求 magento api，返回响应体 对象
     *
     * @param url
     * @param uriVariables
     * @return
     * @throws RestClientException
     */
    public ResponseEntity<String> getForEntity(String url, Map<String, String> uriVariables) throws RestClientException {
        return this.restTemplate.exchange(SERVER_DOMAIN + url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class, uriVariables);
    }

    public ResponseEntity<String> getForEntity(String url) throws RestClientException {
        return this.restTemplate.exchange(SERVER_DOMAIN + url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
    }
}
