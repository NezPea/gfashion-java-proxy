package com.gfashion.api;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.domain.homepage.CustomizedHomepage;
import com.gfashion.restclient.MagentoCustomerClient;
import com.gfashion.restclient.magento.exception.CustomerCreationException;
import com.gfashion.restclient.magento.exception.CustomerNotFoundException;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GfCustomer> creatCustomer(@RequestBody GfCustomerRegistration customer) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(magentoCustomerClient.createCustomer(customer));
        } catch (CustomerCreationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorMessage());
        } catch (CustomerUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<GfCustomer> getCustomerById(@PathVariable Integer customerId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.getCustomerById(customerId));
        } catch (CustomerNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CustomerUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }

    }
}