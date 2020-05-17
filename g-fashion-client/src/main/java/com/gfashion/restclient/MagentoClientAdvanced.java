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

    private static final String ADMIN_TOKEN = "hx1h9quzac7aie63tbfmdw6jhtr2ji7s";
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

    public String getForBody(String url, Object... uriVariables) throws RestClientException {
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(SERVER_DOMAIN + url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class, uriVariables);
        return responseEntity.getBody();
    }

    public ResponseEntity<String> getForEntity(String url, Object... uriVariables) throws RestClientException {
        return this.restTemplate.exchange(SERVER_DOMAIN + url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class, uriVariables);
    }

    /**
     * post
     *
     * @param url
     * @param Params
     * @param uriVariables
     * @return
     * @throws RestClientException
     */
    public String postForObject(String url, Map<String, String> Params, Object... uriVariables) throws RestClientException {
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(SERVER_DOMAIN + url, HttpMethod.POST, new HttpEntity<>(Params, httpHeaders), String.class, uriVariables);
        return responseEntity.getBody();
    }

    public ResponseEntity<String> postForEntity(String url, Map<String, String> Params, Object... uriVariables) throws RestClientException {
        return this.restTemplate.exchange(SERVER_DOMAIN + url, HttpMethod.POST, new HttpEntity<>(Params, httpHeaders), String.class, uriVariables);

    }
}
