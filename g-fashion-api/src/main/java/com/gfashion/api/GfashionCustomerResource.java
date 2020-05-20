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
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionCustomerResource {
    private MagentoCustomerClient magentoCustomerClient;

    @PostMapping("/customers")
    public GfCustomer creatCustomer(@RequestBody GfCustomerRegistration customer) {
        return magentoCustomerClient.createCustomer(customer);
    }

    @GetMapping("/customers/{customerId}")
    public GfCustomer getCustomerById(@PathVariable Integer customerId) {
        return magentoCustomerClient.getCustomerById(customerId);
    }
}