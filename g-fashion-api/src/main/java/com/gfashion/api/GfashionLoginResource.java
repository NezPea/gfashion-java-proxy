package com.gfashion.api;

import com.gfashion.domain.customer.GfCustomerLogin;
import com.gfashion.restclient.MagentoCustomerClient;
import com.gfashion.restclient.magento.exception.CustomerTokenNotFoundException;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST controller for G-Fashion login page
 */
@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionLoginResource {
    private final MagentoCustomerClient magentoCustomerClient;

    @PostMapping("/login")
    public ResponseEntity<String> creatCustomer(@RequestBody GfCustomerLogin customerLogin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.customerLogin(customerLogin));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CustomerUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }
}
