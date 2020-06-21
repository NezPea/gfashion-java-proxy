package com.gfashion.magento.client;

import com.gfashion.domain.customer.GfCustomerLogin;
import com.gfashion.magento.exception.CustomerException;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class RestClient {

    @Value("${magento.url.base}")
    private String baseUrl;
    @Value("${magento.url.adminToken}")
    private String adminTokenUrl;
    @Value("${magento.url.customerToken}")
    private String customerTokenUrl;
    @Value("${magento.username}")
    private String username;
    @Value("${magento.password}")
    private String password;

    private final RestTemplate restTemplate;
    private final Gson gson;

    public RestClient(RestTemplate client, Gson gson) {
        restTemplate = client;
        this.gson = gson;
    }

    private String getAdminToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject adminInfo = new JSONObject();
        adminInfo.put("username", username);
        adminInfo.put("password", password);

        HttpEntity<String> request = new HttpEntity<>(adminInfo.toString(), headers);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(baseUrl + adminTokenUrl, request, String.class);

        return Objects.requireNonNull(responseEntity.getBody()).replace("\"", "");
    }

    public ResponseEntity<String> getCustomerToken(GfCustomerLogin customerLogin) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(customerLogin), headers);
        return restTemplate.postForEntity(baseUrl + customerTokenUrl, request, String.class);
    }

    public HttpHeaders getHeaders(MultiValueMap<String, String> customerHeaders) {

        HttpHeaders headers = new HttpHeaders();

        if (customerHeaders != null) {
            headers.addAll(customerHeaders);
        } else {
            String adminToken = getAdminToken();
            headers.setBearerAuth(adminToken);
        }

        if (headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        return headers;
    }

    public HttpHeaders getCustomerHeaders(String customerToken) throws CustomerException {
        if (customerToken == null) {
            throw new CustomerException(HttpStatus.NOT_FOUND, "Customer token not found");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, customerToken);
        return headers;
    }

    public <T> ResponseEntity<T> postForEntity(String relativeUrl, Object entity, Class<T> responseType, MultiValueMap<String, String> customerHeaders) {
        HttpHeaders headers = getHeaders(customerHeaders);
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(entity), headers);
        return restTemplate.postForEntity(baseUrl + relativeUrl, request, responseType);
    }

    public <T> ResponseEntity<T> exchangeGet(String relativeUrl, Class<T> responseType, MultiValueMap<String, String> customerHeaders) {
        HttpHeaders headers = getHeaders(customerHeaders);
        return restTemplate.exchange(baseUrl + relativeUrl, HttpMethod.GET, new HttpEntity<>(headers), responseType);
    }

    public <T> ResponseEntity<T> exchangePut(String relativeUrl, Object entity, Class<T> responseType, MultiValueMap<String, String> customerHeaders) {
        HttpHeaders headers = getHeaders(customerHeaders);
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(entity), headers);
        return restTemplate.exchange(baseUrl + relativeUrl, HttpMethod.PUT, request, responseType);
    }

    public <T> ResponseEntity<T> exchangeDelete(String relativeUrl, Class<T> responseType, MultiValueMap<String, String> customerHeaders) {
        HttpHeaders headers = getHeaders(customerHeaders);
        return restTemplate.exchange(baseUrl + relativeUrl, HttpMethod.DELETE, new HttpEntity<>(headers), responseType);
    }
}
