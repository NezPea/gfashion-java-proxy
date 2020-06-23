package com.gfashion.api.magento;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;
import com.gfashion.domain.customer.GfCustomerNewPassword;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.magento.client.MagentoCustomerClient;
import com.gfashion.magento.client.SendGridEmailClient;
import com.gfashion.magento.exception.CustomerException;
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
    public ResponseEntity<GfCustomer> creatCustomer(@RequestBody GfCustomerRegistration gfCustomer, @RequestHeader(name = "Authorization") String token) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(magentoCustomerClient.createCustomer(gfCustomer, token));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<GfCustomer> getCustomerById(@PathVariable Integer customerId, @RequestHeader(name = "Authorization") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.getCustomerById(customerId, token));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<GfCustomer> updateCustomerById(@RequestBody GfCustomerRegistration gfCustomer, @PathVariable Integer customerId, @RequestHeader(name = "Authorization") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.updateCustomerById(gfCustomer, customerId, token));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Integer customerId, @RequestHeader(name = "Authorization") String token) {
        try {
            magentoCustomerClient.deleteCustomerById(customerId, token);
            return ResponseEntity.status(HttpStatus.OK).body("The Customer " + customerId + " has been deleted.");
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

    @PutMapping("/customers/{customerId}/addAddress")
    public ResponseEntity<GfCustomer> addAddress(@RequestBody GfCustomerAddress newAddress, @PathVariable Integer customerId, @RequestHeader(name = "Authorization") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.addAddress(newAddress, customerId, token));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PutMapping("/customers/{customerId}/changeAddress")
    public ResponseEntity<GfCustomer> changeAddress(@RequestBody GfCustomerAddress newAddress, @PathVariable Integer customerId, @RequestHeader(name = "Authorization") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.changeAddress(newAddress, customerId, token));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @DeleteMapping("/customers/{customerId}/deleteAddress/{addressId}")
    public ResponseEntity<GfCustomer> deleteAddress(@PathVariable Integer customerId, @PathVariable Integer addressId, @RequestHeader(name = "Authorization") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCustomerClient.deleteAddress(customerId, addressId, token));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/customers/{customerId}/getVerificationCode")
    public ResponseEntity<String> getVerificationCode(@RequestBody String email, @PathVariable Integer customerId, @RequestHeader(name = "Authorization") String token) {
        try {
            magentoCustomerClient.verifyCustomerToken(customerId, token);
            return ResponseEntity.status(HttpStatus.OK).body(sendGridEmailClient.sendVerificationCode(email));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }

    }

}