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
public class GfashionCustomerPasswordResource {

    private MagentoRestClient client;

    GfashionCustomerPasswordResource(MagentoRestClient client) {
        this.client = client;
    }

    @PutMapping("/customers/password")
    public GfResponse password(@RequestBody GfCustomersPassword gfCustomersPassword, HttpServletResponse response) {
        GfResponse gfResponse = client.putCustomersPassword(gfCustomersPassword);
        response.setStatus(gfResponse.getStatusCode());
        return gfResponse;
    }

    @PostMapping("/customers/resetPassword")
    public GfResponse resetPassword(@RequestBody GfCustomersResetPassword gfCustomersResetPassword, HttpServletResponse response) {
        GfResponse gfResponse = client.postCustomersResetPassword(gfCustomersResetPassword);
        response.setStatus(gfResponse.getStatusCode());
        return gfResponse;
    }

}