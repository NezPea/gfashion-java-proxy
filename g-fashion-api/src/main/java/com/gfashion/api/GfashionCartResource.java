package com.gfashion.api;

import com.gfashion.domain.cart.GfCartEstimateShippingMethod;
import com.gfashion.domain.cart.GfCartItem;
import com.gfashion.domain.cart.GfCart;
import com.gfashion.domain.cart.GfShippingAddress;
import com.gfashion.restclient.MagentoCartClient;
import com.gfashion.restclient.magento.exception.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GfashionCartResource.class);

    private final MagentoCartClient magentoCartClient;

    @GetMapping("/carts")
    public ResponseEntity<GfCart> getCart(@RequestHeader(name = "Authorization") String customerToken) {
        LOGGER.debug(customerToken);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getCart(customerToken));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        } catch (CartItemUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @GetMapping("/carts/items")
    public ResponseEntity<List<GfCartItem>> getCartItemList(@RequestHeader(name = "Authorization") String customerToken) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getCartItemList(customerToken));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartItemUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @PostMapping("/carts/items")
    public ResponseEntity<GfCartItem> addCartItem(@RequestHeader(name = "Authorization") String customerToken, @RequestBody GfCartItem cartItem) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.addCartItem(customerToken, cartItem));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartItemCreationException e) {
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
        } catch (CartItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartItemUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @DeleteMapping("/carts/items/{cartItemId}")
    public ResponseEntity<Boolean> deleteCartItem(@RequestHeader(name = "Authorization") String customerToken, @PathVariable Integer cartItemId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.deleteCartItem(customerToken, cartItemId));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartItemNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (CartItemUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    @PostMapping("/carts/estimate-shipping")
    public ResponseEntity<List<GfCartEstimateShippingMethod>> getEstimateShipping(@RequestHeader(name = "Authorization") String customerToken, @RequestBody GfShippingAddress address) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoCartClient.getEstimateShippingMethods(customerToken, address));
        } catch (CustomerTokenNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
        } catch (CartShippingAddressGetException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorMessage());
        } catch (CartUnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }
}
