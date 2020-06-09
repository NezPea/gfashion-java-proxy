package com.gfashion.api;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerNewPassword;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.restclient.MagentoCustomerClient;
import com.gfashion.restclient.SendGridEmailClient;
import com.gfashion.restclient.magento.exception.*;
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
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionCustomerResource {

    private MagentoCustomerClient magentoCustomerClient;

    private SendGridEmailClient sendGridEmailClient;

    @PostMapping("/customers")
    public ResponseEntity<GfCustomer> creatCustomer(@RequestBody GfCustomerRegistration gfCustomer) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(magentoCustomerClient.createCustomer(gfCustomer));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<GfCustomer> getCustomerById(@PathVariable Integer customerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.getCustomerById(customerId));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<GfCustomer> getCustomerById(@RequestBody GfCustomerRegistration gfCustomer, @PathVariable Integer customerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.updateCustomerById(gfCustomer, customerId));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PutMapping("/customers/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody GfCustomerNewPassword newPassword, @RequestHeader(name = "Authorization") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.changePassword(newPassword, token));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/customers/{customerId}/getVerificationCode")
    public ResponseEntity<String> getVerificationCode(@RequestBody String email, @PathVariable String customerId, @RequestHeader(name = "Authorization") String token) {
        try {
            magentoCustomerClient.verifyCustomerToken(customerId, token);
            return ResponseEntity.status(HttpStatus.OK).body(sendGridEmailClient.sendVerificationCode(email));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }

    }

}