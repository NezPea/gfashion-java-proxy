package com.gfashion.api;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.restclient.MagentoCustomerClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for G-Fashion order page
 */
@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
@AllArgsConstructor
public class GfashionCustomerResource {
    private MagentoCustomerClient _client;

    @PostMapping("/customer")
    public GfCustomer creatCustomer(@RequestBody GfCustomerRegistration customer) {
        return this._client.createCustomer(customer);
    }

    @GetMapping("/customer/{customerId}")
    public GfCustomer getCustomerById(@PathVariable Integer customerId) {
        return this._client.getCustomerById(customerId);
    }
}