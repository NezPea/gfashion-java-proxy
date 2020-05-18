package com.gfashion.api;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.restclient.MagentoCustomerClient;
import com.gfashion.domain.customer.GfCustomerRegistration;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
public class GfashionCustomerResource {
    /**
     * REST controller for G-Fashion order page
     */
    private MagentoCustomerClient _client;
    GfashionCustomerResource(MagentoCustomerClient client){
        this._client = client;
    }


    @PostMapping("/customer")
    public GfCustomer creatCustomer(@RequestBody GfCustomerRegistration customer){
        return this._client.createCustomer(customer);
    }

    @GetMapping("/customer/{customerId}")
    public GfCustomer getCustomerById(@PathVariable Integer customerId){
        return this._client.getCustomerById(customerId);
    }
}