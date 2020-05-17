package com.gfashion.api;

import com.gfashion.domain.GfResponse;
import com.gfashion.domain.customer.GfCustomerLogin;
import com.gfashion.restclient.MagentoRestClient;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
public class GfashionCustomerAccountLoginResource {

    /**
     * REST controller for G-Fashion order page
     */
    private MagentoRestClient _client;

    GfashionCustomerAccountLoginResource(MagentoRestClient client) {
        this._client = client;
    }

    @PostMapping("/customer/account/login")
    public GfResponse login(@RequestBody GfCustomerLogin gfCustomerLogin, HttpServletResponse response) {
        GfResponse gfResponse = this._client.getCustomerToken(gfCustomerLogin);
        response.setStatus(gfResponse.getStatusCode());
        return gfResponse;
    }

}