package com.gfashion.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class MagentoClient {

    private RestTemplate rest;

    public MagentoClient(RestTemplate rest) {
        this.rest = rest;
    }

    //
    // GET examples
    //

    /*
     * Specify parameter as varargs argument
     */
    public List<Object> getCountries() {
        return rest.getForObject("https://www.gfashion2020.tk/rest/default/V1/directory/countries", List.class);
    }
}
