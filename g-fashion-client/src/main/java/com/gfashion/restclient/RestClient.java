package com.gfashion.restclient;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@AllArgsConstructor
public class RestClient {

    private MegentoConfigProperties magentoConfig;

    private RestTemplate _client;

    private String getAdminToken(){
        String adminUrl = magentoConfig.getUrl().get("base") + magentoConfig.getUrl().get("token");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject adminInfo= new JSONObject();
        adminInfo.put("username", magentoConfig.getUsername() );
        adminInfo.put("password", magentoConfig.getPassword());

        HttpEntity<String> request = new HttpEntity<String>(adminInfo.toString(), headers);
        ResponseEntity<String> responseEntity =
                this._client.postForEntity(adminUrl, request, String.class);

        return Objects.requireNonNull(responseEntity.getBody()).replace("\"", "");
    }

    public HttpHeaders getDefaultHeaders(MultiValueMap<String, String> extraHeaders){
        HttpHeaders headers = new HttpHeaders();
        if(extraHeaders != null){
            headers.addAll(extraHeaders);
        }else{
            String adminToken = getAdminToken();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(adminToken);
        }
        return headers;
    }

    public <T> ResponseEntity<T> postForEntity(String relativeUrl, Object entity, Class<T> responseType, MultiValueMap<String, String> extraHeaders){
        String url = magentoConfig.getUrl().get("base") + relativeUrl;
        HttpHeaders headers = getDefaultHeaders(extraHeaders);

        Gson gson = new Gson();
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(entity), headers);
        return this._client.postForEntity(url, request, responseType);
    }

    public <T> ResponseEntity<T> exchangeGet(String relativeUrl, Class<T> responseType, MultiValueMap<String, String> extraHeaders){
        String url = magentoConfig.getUrl().get("base") + relativeUrl;
        HttpHeaders headers = getDefaultHeaders(extraHeaders);
        return this._client.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
    }

}
