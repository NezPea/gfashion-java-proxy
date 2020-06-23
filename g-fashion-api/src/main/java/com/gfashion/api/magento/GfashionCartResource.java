package com.gfashion.api.magento;

import com.gfashion.domain.cart.*;
import com.gfashion.magento.client.MagentoCartClient;
import com.gfashion.magento.exception.CartException;
import com.gfashion.magento.exception.CustomerException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST controller for G-Fashion cart page
 */
@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionCartResource {

    private final MagentoCartClient magentoCartClient;

    @GetMapping("/carts")
    public ResponseEntity<GfCart> getCart(@RequestHeader(name = "Authorization") String customerToken) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getCart(customerToken));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @GetMapping("/carts/items")
    public ResponseEntity<List<GfCartItem>> getCartItemList(
            @RequestHeader(name = "Authorization") String customerToken) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getCartItemList(customerToken));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/items")
    public ResponseEntity<GfCartItem> addCartItem(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartItem cartItem) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.addCartItem(customerToken, cartItem));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PutMapping("/carts/items/{cartItemId}")
    public ResponseEntity<GfCartItem> updateCartItem(
            @RequestHeader(name = "Authorization") String customerToken,
            @PathVariable Integer cartItemId, @RequestBody GfCartItem cartItem) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.updateCartItem(customerToken, cartItemId, cartItem));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @DeleteMapping("/carts/items/{cartItemId}")
    public ResponseEntity<Boolean> deleteCartItem(
            @RequestHeader(name = "Authorization") String customerToken,
            @PathVariable Integer cartItemId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.deleteCartItem(customerToken, cartItemId));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/estimate-shipping")
    public ResponseEntity<List<GfCartEstimateShippingMethod>> getEstimateShipping(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartAddress address) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getEstimateShippingMethods(customerToken, address));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/shipping-information")
    public ResponseEntity<GfCartShippingInformation> setShippingInformation(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartAddressInformation addressInformation) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.setShippingInformation(customerToken, addressInformation));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @GetMapping("/carts/payment-methods")
    public ResponseEntity<List<GfCartPaymentMethod>> getPaymentMethods(
            @RequestHeader(name = "Authorization") String customerToken) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getPaymentMethods(customerToken));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/payment-information")
    public ResponseEntity<String> createOrder(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartPaymentInformation paymentInformation) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.createOrder(customerToken, paymentInformation));
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }
}
