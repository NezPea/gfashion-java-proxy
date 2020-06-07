package com.gfashion.restclient;

import com.gfashion.domain.cart.*;
import com.gfashion.restclient.magento.customer.*;
import com.gfashion.restclient.magento.exception.*;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MagentoCartClient {

    public static final String CART_ITEMS_PATH = "items/";

    @Value("${magento.url.carts}")
    private String cartsUrl;

    @Autowired
    private RestClient restClient;

    private final GfMagentoConverter mapper = Mappers.getMapper(GfMagentoConverter.class);

    public GfCart getCart(String customerToken) throws CustomerTokenNotFoundException, CartNotFoundException, CartUnknownException {
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            restClient.postForEntity(cartsUrl, null, Integer.class, headers);

            ResponseEntity<String> responseEntity = restClient.exchangeGet(cartsUrl, String.class, headers);
            Gson gson = new Gson();
            GfCart gfCart = mapper.convertMagentoCartToGfCart(gson.fromJson(responseEntity.getBody(), MagentoCart.class));
            List<GfCartItem> cartItems = getCartItemList(customerToken);
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

    public List<GfCartItem> getCartItemList(String customerToken) throws CustomerTokenNotFoundException, CartNotFoundException, CartUnknownException {
        String cartItemsUrl = cartsUrl + CART_ITEMS_PATH;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            ResponseEntity<String> responseEntity = restClient.exchangeGet(cartItemsUrl, String.class, headers);

            Gson gson = new Gson();
            String body = responseEntity.getBody();
            Type listType = new TypeToken<List<MagentoCartItem>>() {}.getType();
            List<MagentoCartItem> cartItems = gson.fromJson(body, listType);
            if (cartItems == null) {
                throw new CartNotFoundException("Cart item not found");
            }
            return cartItems.stream().map(mapper::convertMagentoCartItemToGfCartItem).collect(Collectors.toList());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartNotFoundException(e.getMessage());
            }
            throw new CartUnknownException(e.getMessage());
        }
    }

    public GfCartItem addCartItem(String customerToken, GfCartItem cartItem) throws CustomerTokenNotFoundException, CartCreationException, CartUnknownException {
        String cartItemsUrl = cartsUrl + CART_ITEMS_PATH;
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            Map<String, Object> body = new HashMap<>();
            body.put("cartItem", cartItem);
            ResponseEntity<String> responseEntity = restClient.postForEntity(cartItemsUrl, body, String.class, headers);

            Gson gson = new Gson();
            return mapper.convertMagentoCartItemToGfCartItem(gson.fromJson(responseEntity.getBody(), MagentoCartItem.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartCreationException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CartCreationException(e.getMessage());
            }
            throw new CartUnknownException(e.getMessage());
        }
    }

    public GfCartItem updateCartItem(String customerToken, Integer cartItemId, GfCartItem cartItem) throws CustomerTokenNotFoundException, CartNotFoundException, CartUnknownException {
        String updateCartItemUrl = cartsUrl + CART_ITEMS_PATH + cartItemId;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            Map<String, Object> body = new HashMap<>();
            body.put("cartItem", cartItem);
            ResponseEntity<String> responseEntity = restClient.exchangePut(updateCartItemUrl, body, String.class, headers);

            Gson gson = new Gson();
            return mapper.convertMagentoCartItemToGfCartItem(gson.fromJson(responseEntity.getBody(), MagentoCartItem.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartNotFoundException(e.getMessage());
            }
            throw new CartUnknownException(e.getMessage());
        }
    }

    public Boolean deleteCartItem(String customerToken, Integer cartItemId) throws CustomerTokenNotFoundException, CartNotFoundException, CartUnknownException {
        String deleteCartItemUrl = cartsUrl + CART_ITEMS_PATH + cartItemId;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            ResponseEntity<Boolean> responseEntity = restClient.exchangeDelete(deleteCartItemUrl, Boolean.class, headers);

            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartNotFoundException(e.getMessage());
            }
            throw new CartUnknownException(e.getMessage());
        }
    }

    public List<GfCartEstimateShippingMethod> getEstimateShippingMethods(String customerToken, GfCartAddress address) throws CustomerTokenNotFoundException, CartGetException, CartUnknownException {
        String cartEstimateShippingMethodsUrl = cartsUrl + "estimate-shipping-methods/";
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            Map<String, Object> body = new HashMap<>();
            body.put("address", address);
            ResponseEntity<String> responseEntity = restClient.postForEntity(cartEstimateShippingMethodsUrl, body, String.class, headers);

            Gson gson = new Gson();

            Type listType = new TypeToken<List<MagentoCartEstimateShippingMethod>>() {}.getType();
            List<MagentoCartEstimateShippingMethod> methods = gson.fromJson(responseEntity.getBody(), listType);
            if (methods == null) {
                throw new CartGetException("Response is null");
            }
            return methods.stream().map(mapper::convertMagentoCartEstimateShippingMethodToGfCartEstimateShippingMethod).collect(Collectors.toList());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CartGetException(e.getMessage());
            }
            throw new CartUnknownException(e.getMessage());
        }
    }

    public GfCartShippingInformation setShippingInformation(String customerToken, GfCartAddressInformation addressInformation) throws CustomerTokenNotFoundException, CartUnknownException, CartNotFoundException, CartCreationException {
        String cartPaymentMethodsUrl = cartsUrl + "shipping-information/";
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            Map<String, Object> body = new HashMap<>();
            body.put("addressInformation", addressInformation);
            ResponseEntity<String> responseEntity = restClient.postForEntity(cartPaymentMethodsUrl, body, String.class, headers);

            Gson gson = new Gson();
            MagentoCartShippingInformation cartCheckout = gson.fromJson(responseEntity.getBody(), MagentoCartShippingInformation.class);
            return mapper.convertMagentoCartShippingInformationToGfCartShippingInformation(cartCheckout);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerTokenNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CartNotFoundException(e.getMessage());
            }
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CartCreationException(e.getMessage());
            }
            throw new CartUnknownException(e.getMessage());
        }
    }
}
