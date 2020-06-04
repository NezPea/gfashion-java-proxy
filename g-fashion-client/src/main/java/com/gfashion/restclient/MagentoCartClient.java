package com.gfashion.restclient;

import com.gfashion.domain.cart.GFCartItem;
import com.gfashion.domain.cart.GfCart;
import com.gfashion.restclient.magento.customer.MagentoCart;
import com.gfashion.restclient.magento.customer.MagentoCartItem;
import com.gfashion.restclient.magento.exception.*;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.gfashion.restclient.request.GFCartItemRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MagentoCartClient {

    @Value("${magento.url.carts}")
    private String cartsUrl;
    @Value("${magento.url.cartItems}")
    private String cartItemsUrl;

    @Autowired
    private RestClient restClient;

    private final GfMagentoConverter mapper = Mappers.getMapper(GfMagentoConverter.class);

    public GfCart getCart(String customerToken) throws CustomerTokenNotFoundException, CartNotFoundException, CartUnknownException, CartItemUnknownException, CartItemNotFoundException {
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            ResponseEntity<String> responseEntity = restClient.exchangeGet(cartsUrl, String.class, headers);
            Gson gson = new Gson();
            GfCart gfCart = mapper.convertMagentoCartToGfCart(gson.fromJson(responseEntity.getBody(), MagentoCart.class));
            List<GFCartItem> cartItems = getCartItemList(customerToken);
            gfCart.setItems(cartItems);
            return mapper.convertMagentoCartToGfCart(gson.fromJson(responseEntity.getBody(), MagentoCart.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CartNotFoundException(e.getMessage());
            }
            throw new CartUnknownException(e.getMessage());
        }
    }

    public List<GFCartItem> getCartItemList(String customerToken) throws CustomerTokenNotFoundException, CartItemNotFoundException, CartItemUnknownException {
        String updateCartItemUrl = cartItemsUrl;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            ResponseEntity<String> responseEntity = restClient.exchangeGet(updateCartItemUrl, String.class, headers);

            Gson gson = new Gson();
            String body = responseEntity.getBody();
            Type listType = new TypeToken<List<MagentoCartItem>>() {}.getType();
            List<MagentoCartItem> cartItems = gson.fromJson(body, listType);
            if (cartItems == null) {
                throw new CartItemNotFoundException("Cart item not found");
            }
            return cartItems.stream().map(mapper::convertMagentoCartItemToGfCartItem).collect(Collectors.toList());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartItemNotFoundException(e.getMessage());
            }
            throw new CartItemUnknownException(e.getMessage());
        }
    }

    public GFCartItem addCartItem(String customerToken, GFCartItem cartItem) throws CustomerTokenNotFoundException, CartItemCreationException, CartUnknownException {
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            GFCartItemRequest body = new GFCartItemRequest(cartItem);
            ResponseEntity<String> responseEntity = restClient.postForEntity(cartItemsUrl, body, String.class, headers);

            Gson gson = new Gson();
            return mapper.convertMagentoCartItemToGfCartItem(gson.fromJson(responseEntity.getBody(), MagentoCartItem.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartItemCreationException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CartItemCreationException(e.getMessage());
            }
            throw new CartUnknownException(e.getMessage());
        }
    }

    public GFCartItem updateCartItem(String customerToken, Integer cartItemId, GFCartItem cartItem) throws CustomerTokenNotFoundException, CartItemNotFoundException, CartItemUnknownException {
        String updateCartItemUrl = cartItemsUrl + cartItemId;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            GFCartItemRequest body = new GFCartItemRequest(cartItem);
            ResponseEntity<String> responseEntity = restClient.exchangePut(updateCartItemUrl, body, String.class, headers);

            Gson gson = new Gson();
            return mapper.convertMagentoCartItemToGfCartItem(gson.fromJson(responseEntity.getBody(), MagentoCartItem.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartItemNotFoundException(e.getMessage());
            }
            throw new CartItemUnknownException(e.getMessage());
        }
    }

    public Boolean deleteCartItem(String customerToken, Integer cartItemId) throws CustomerTokenNotFoundException, CartItemNotFoundException, CartItemUnknownException {
        String deleteCartItemUrl = cartItemsUrl + cartItemId;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            ResponseEntity<Boolean> responseEntity = restClient.exchangeDelete(deleteCartItemUrl, Boolean.class, headers);

            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartItemNotFoundException(e.getMessage());
            }
            throw new CartItemUnknownException(e.getMessage());
        }
    }
}
