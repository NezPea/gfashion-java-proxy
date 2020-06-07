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

    private final MagentoCartClient magentoCartClient;

    @GetMapping("/carts")
    public ResponseEntity<GfCart> getCart(@RequestHeader(name = "Authorization") String customerToken) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getCart(customerToken));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @GetMapping("/carts/items")
    public ResponseEntity<List<GfCartItem>> getCartItemList(@RequestHeader(name = "Authorization") String customerToken) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getCartItemList(customerToken));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @PostMapping("/carts/items")
    public ResponseEntity<GfCartItem> addCartItem(@RequestHeader(name = "Authorization") String customerToken, @RequestBody GfCartItem cartItem) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.addCartItem(customerToken, cartItem));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartCreationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @PutMapping("/carts/items/{cartItemId}")
    public ResponseEntity<GfCartItem> updateCartItem(@RequestHeader(name = "Authorization") String customerToken, @PathVariable Integer cartItemId, @RequestBody GfCartItem cartItem) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.updateCartItem(customerToken, cartItemId, cartItem));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @DeleteMapping("/carts/items/{cartItemId}")
    public ResponseEntity<Boolean> deleteCartItem(@RequestHeader(name = "Authorization") String customerToken, @PathVariable Integer cartItemId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.deleteCartItem(customerToken, cartItemId));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @PostMapping("/carts/estimate-shipping")
    public ResponseEntity<List<GfCartEstimateShippingMethod>> getEstimateShipping(@RequestHeader(name = "Authorization") String customerToken, @RequestBody GfCartAddress address) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getEstimateShippingMethods(customerToken, address));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartGetException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @PostMapping("/carts/shipping-information")
    public ResponseEntity<GfCartShippingInformation> setShippingInformation(@RequestHeader(name = "Authorization") String customerToken, @RequestBody GfCartAddressInformation addressInformation) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.setShippingInformation(customerToken, addressInformation));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        } catch (CartCreationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorMessage());
        } catch (CartNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        }
    }
}
