package com.gfashion.api;

import com.gfashion.domain.GfResponse;
import com.gfashion.domain.customer.GfCustomerLogin;
import com.gfashion.domain.customer.GfCustomersPassword;
import com.gfashion.domain.customer.GfCustomersResetPassword;
import com.gfashion.restclient.MagentoRestClient;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
public class GfashionCustomerLoginResource {

    private MagentoRestClient client;

    GfashionCustomerLoginResource(MagentoRestClient client) {
        this.client = client;
    }

    @PostMapping("/customer/login")
    public GfResponse login(@RequestBody GfCustomerLogin gfCustomerLogin, HttpServletResponse response) {
        GfResponse gfResponse = client.getCustomerToken(gfCustomerLogin);
        response.setStatus(gfResponse.getStatusCode());
        return gfResponse;
    }
}