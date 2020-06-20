package com.gfashion.api;

import com.gfashion.domain.cart.*;
import com.gfashion.restclient.MagentoCartClient;
import com.gfashion.restclient.magento.exception.*;
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

    private final MagentoCartClient client;

    @GetMapping("/carts")
    public ResponseEntity<GfCart> getCart(@RequestHeader(name = "Authorization") String customerToken) {
        try {
            GfCart cart = client.getCart(customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(cart);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @GetMapping("/carts/items")
    public ResponseEntity<List<GfCartItem>> getCartItemList(
            @RequestHeader(name = "Authorization") String customerToken) {
        try {
            List<GfCartItem> cartItems = client.getCartItemList(customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(cartItems);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/items")
    public ResponseEntity<GfCartItem> addCartItem(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartItem cartItem) {
        try {
            GfCartItem item = client.addCartItem(customerToken, cartItem);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PutMapping("/carts/items/{cartItemId}")
    public ResponseEntity<GfCartItem> updateCartItem(
            @RequestHeader(name = "Authorization") String customerToken,
            @PathVariable Integer cartItemId, @RequestBody GfCartItem cartItem) {
        try {
            GfCartItem item = client.updateCartItem(customerToken, cartItemId, cartItem);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @DeleteMapping("/carts/items/{cartItemId}")
    public ResponseEntity<Boolean> deleteCartItem(
            @RequestHeader(name = "Authorization") String customerToken,
            @PathVariable Integer cartItemId) {
        try {
            Boolean result = client.deleteCartItem(customerToken, cartItemId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/estimate-shipping")
    public ResponseEntity<List<GfCartEstimateShippingMethod>> getEstimateShipping(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartAddress address) {
        try {
            List<GfCartEstimateShippingMethod> methods = client.getEstimateShippingMethods(customerToken, address);
            return ResponseEntity.status(HttpStatus.OK).body(methods);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/shipping-information")
    public ResponseEntity<GfCartShippingInfo> setShippingInformation(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartAddressInformation addressInformation) {
        try {
            GfCartShippingInfo shippingInfo = client.setShippingInformation(customerToken, addressInformation);
            return ResponseEntity.status(HttpStatus.OK).body(shippingInfo);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @GetMapping("/carts/payment-methods")
    public ResponseEntity<List<GfCartPaymentMethod>> getPaymentMethods(
            @RequestHeader(name = "Authorization") String customerToken) {
        try {
            List<GfCartPaymentMethod> paymentMethods = client.getPaymentMethods(customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(paymentMethods);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/payment-information")
    public ResponseEntity<String> createOrder(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartPaymentInfo paymentInformation) {
        try {
            String orderId = client.createOrder(customerToken, paymentInformation);
            return ResponseEntity.status(HttpStatus.OK).body(orderId);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/retrieve-adyen-payment-methods")
    public ResponseEntity<List<GfCartAdyenPaymentMethod>> getAdyenPaymentMethods(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartAdyenPaymentParam adyenPaymentParam) {
        try {
            List<GfCartAdyenPaymentMethod> methods = client.getAdyenPaymentMethods(customerToken, adyenPaymentParam);
            return ResponseEntity.status(HttpStatus.OK).body(methods);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/adyen-payment-information")
    public ResponseEntity<GfCartAdyenOrder> createAdyenOrder(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartAdyenPaymentInfo paymentInfo) {
        try {
            GfCartAdyenOrder adyenOrder = client.createAdyenOrder(customerToken, paymentInfo);
            return ResponseEntity.status(HttpStatus.OK).body(adyenOrder);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @PostMapping("/carts/adyen-threeds2-process")
    public ResponseEntity<GfCartAdyenPaymentStatus> handleAdyenThreeDS2Process(
            @RequestHeader(name = "Authorization") String customerToken,
            @RequestBody GfCartAdyenThreeDS2Process threeDS2Process) {
        try {
            GfCartAdyenPaymentStatus paymentStatus = client.handleAdyenThreeDS2Process(customerToken, threeDS2Process);
            return ResponseEntity.status(HttpStatus.OK).body(paymentStatus);
        } catch (CustomerException | CartException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }
}
