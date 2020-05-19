package com.gfashion.restclient;

import com.gfashion.domain.Gf4xx5xxResponse;
import com.gfashion.domain.GfResponse;
import com.gfashion.domain.customer.GfCustomerLogin;
import com.gfashion.domain.customer.GfCustomerLoginResponse;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.domain.customer.GfCustomersPassword;
import com.gfashion.domain.customer.GfCustomersResetPassword;
import com.gfashion.restclient.magento.MagentoCustomer;
import com.gfashion.restclient.magento.MagentoObjectConverter;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MagentoRestClient {

    private final String baseUrl = "https://www.gfashion2020.tk/index.php/rest/V1";

    @Autowired
    private RestTemplate _client;

    @Autowired
    private MagentoObjectConverter _daoConverter;

    public String getToken() {
        String adminUrl = baseUrl + "/integration/admin/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject adminInfo = new JSONObject();
        adminInfo.put("username", "admin");
        adminInfo.put("password", "202064_Hh");

        HttpEntity<String> request = new HttpEntity<String>(adminInfo.toString(), headers);
        ResponseEntity<String> responseEntity =
            this._client.postForEntity(adminUrl, request, String.class);

        String adminToken = responseEntity.getBody().replace("\"", "");
        return adminToken;
    }

    public GfCustomerRegistration createCustomer(GfCustomerRegistration customerRegistration) {
        String adminToken = getToken();
        String createCustomerUrl = baseUrl + "/customers";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        Gson gson = new Gson();
        HttpEntity<String> request = new HttpEntity<String>(gson.toJson(customerRegistration), headers);
        ResponseEntity<String> responseEntity = this._client.postForEntity(createCustomerUrl, request, String.class);

        return this._daoConverter.ConvertDtoToGfCustomerRegistration(gson.fromJson(responseEntity.getBody(), MagentoCustomer.class));
    }

    public GfCustomerRegistration getCustomerById(Integer customerId) {
        String adminToken = getToken();
        String getCustomerUrl = baseUrl + "/customers/" + customerId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        ResponseEntity<String> responseEntityProduct = this._client.exchange(
            getCustomerUrl,
            HttpMethod.GET, new HttpEntity<Object>(headers),
            String.class);
        String customer = responseEntityProduct.getBody();

        Gson gson = new Gson();
        MagentoCustomer dto = gson.fromJson(customer, MagentoCustomer.class);

        return this._daoConverter.ConvertDtoToGfCustomerRegistration(dto);
    }

    public GfResponse getCustomerToken(GfCustomerLogin gfCustomerLogin) {
        String url = baseUrl + "/integration/customer/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(gfCustomerLogin), headers);
        ResponseEntity<String> responseEntity = _client.postForEntity(url, request, String.class);

        GfResponse gfResponse;
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            gfResponse = new GfCustomerLoginResponse(responseEntity.getBody().replace("\"", ""));
        } else {
            gfResponse = gson.fromJson(responseEntity.getBody(), Gf4xx5xxResponse.class);
        }
        gfResponse.setStatusCode(responseEntity.getStatusCodeValue());
        return gfResponse;
    }

    public GfResponse putCustomersPassword(GfCustomersPassword gfCustomersPassword) {
        String url = baseUrl + "/customers/password";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(gfCustomersPassword), headers);
        ResponseEntity<String> responseEntity = _client.exchange(url, HttpMethod.PUT, request, String.class);

        GfResponse gfResponse;
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            gfResponse = new GfResponse();
        } else {
            gfResponse = gson.fromJson(responseEntity.getBody(), Gf4xx5xxResponse.class);
        }
        gfResponse.setStatusCode(responseEntity.getStatusCodeValue());
        return gfResponse;
    }

    public GfResponse postCustomersResetPassword(GfCustomersResetPassword gfCustomersResetPassword) {
        String url = baseUrl + "/customers/resetPassword";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(gfCustomersResetPassword), headers);
        ResponseEntity<String> responseEntity = _client.postForEntity(url, request, String.class);

        GfResponse gfResponse;
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            gfResponse = new GfResponse();
        } else {
            gfResponse = gson.fromJson(responseEntity.getBody(), Gf4xx5xxResponse.class);
        }
        gfResponse.setStatusCode(responseEntity.getStatusCodeValue());
        return gfResponse;
    }
}

