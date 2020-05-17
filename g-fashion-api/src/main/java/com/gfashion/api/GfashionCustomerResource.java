package com.gfashion.api;

import com.gfashion.restclient.MagentoRestClient;
import com.gfashion.domain.customer.GfCustomerRegistration;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
public class GfashionCustomerResource {
    /**
     * REST controller for G-Fashion order page
     */
    private MagentoRestClient _client;
    GfashionCustomerResource(MagentoRestClient client){
        this._client = client;
    }


    @PostMapping("/customer")
    public GfCustomerRegistration creatCustomer(@RequestBody GfCustomerRegistration customer){
        return this._client.createCustomer(customer);
    }

    @GetMapping("/customer/{customerId}")
    public GfCustomerRegistration getCustomerById(@PathVariable Integer customerId){
        return this._client.getCustomerById(customerId);
    }
}