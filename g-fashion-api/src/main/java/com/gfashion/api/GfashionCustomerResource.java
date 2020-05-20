package com.gfashion.api;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.restclient.MagentoCustomerClient;
import com.gfashion.restclient.magento.exception.CustomerCreationException;
import com.gfashion.restclient.magento.exception.CustomerNotFoundException;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try{
            return magentoCustomerClient.createCustomer(customer);
        } catch (CustomerCreationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer Not Created, Pls examine your input parameters", e);
        } catch (CustomerUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Other exception", e);
        }

    }

    @GetMapping("/customers/{customerId}")
    public GfCustomer getCustomerById(@PathVariable Integer customerId) {
        try{
            return magentoCustomerClient.getCustomerById(customerId);
        } catch (CustomerNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found", e);
        } catch (CustomerUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Other exception", e);
        }

    }
}