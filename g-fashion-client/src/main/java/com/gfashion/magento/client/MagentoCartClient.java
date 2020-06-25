package com.gfashion.magento.client;

import com.gfashion.domain.cart.*;
import com.gfashion.magento.entity.cart.*;
import com.gfashion.magento.exception.CartException;
import com.gfashion.magento.exception.CustomerException;
import com.gfashion.magento.mapper.GfMagentoCartConverter;
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

    @Autowired
    private Gson gson;

    private final GfMagentoCartConverter mapper = Mappers.getMapper(GfMagentoCartConverter.class);

    public GfCart getCart(String customerToken) throws CustomerException, CartException {
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);

            GfCart gfCart = mapper.convertMagentoCartToGfCart(
                    restClient.exchangeGet(cartsUrl, MagentoCart.class, headers).getBody());
            gfCart.setItems(getCartItemList(customerToken));
            return gfCart;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<GfCartItem> getCartItemList(String customerToken) throws CustomerException, CartException {
        String cartItemsUrl = cartsUrl + CART_ITEMS_PATH;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            ResponseEntity<String> responseEntity = restClient.exchangeGet(cartItemsUrl, String.class, headers);

            String body = responseEntity.getBody();
            Type listType = new TypeToken<List<MagentoCartItem>>() {
            }.getType();
            List<MagentoCartItem> cartItems = gson.fromJson(body, listType);
            if (cartItems == null) {
                throw new CartException(HttpStatus.NOT_FOUND, "Cart item not found");
            }
            return cartItems.stream().map(mapper::convertMagentoCartItemToGfCartItem).collect(Collectors.toList());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public GfCartItem addCartItem(String customerToken, GfCartItem cartItem) throws CustomerException, CartException {
        String cartItemsUrl = cartsUrl + CART_ITEMS_PATH;
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            Map<String, Object> body = new HashMap<>();
            body.put("cartItem", mapper.convertGfCartItemToMagentoCartItem(cartItem));
            ResponseEntity<String> responseEntity = restClient.postForEntity(cartItemsUrl, body, String.class, headers);

            return mapper.convertMagentoCartItemToGfCartItem(gson.fromJson(responseEntity.getBody(), MagentoCartItem.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public GfCartItem updateCartItem(String customerToken, Integer cartItemId, GfCartItem cartItem) throws CustomerException, CartException {
        String updateCartItemUrl = cartsUrl + CART_ITEMS_PATH + cartItemId;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            Map<String, Object> body = new HashMap<>();
            body.put("cartItem", mapper.convertGfCartItemToMagentoCartItem(cartItem));
            ResponseEntity<String> responseEntity = restClient.exchangePut(updateCartItemUrl, body, String.class, headers);

            return mapper.convertMagentoCartItemToGfCartItem(gson.fromJson(responseEntity.getBody(), MagentoCartItem.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Boolean deleteCartItem(String customerToken, Integer cartItemId) throws CustomerException, CartException {
        String deleteCartItemUrl = cartsUrl + CART_ITEMS_PATH + cartItemId;

        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            ResponseEntity<Boolean> responseEntity = restClient.exchangeDelete(deleteCartItemUrl, Boolean.class, headers);

            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<GfCartEstimateShippingMethod> getEstimateShippingMethods(String customerToken, GfCartAddress address) throws CustomerException, CartException {
        String cartEstimateShippingMethodsUrl = cartsUrl + "estimate-shipping-methods/";
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            Map<String, Object> body = new HashMap<>();
            body.put("address", mapper.convertGfCartAddressToMagentoCartAddress(address));
            ResponseEntity<String> responseEntity = restClient.postForEntity(cartEstimateShippingMethodsUrl, body, String.class, headers);

            Type listType = new TypeToken<List<MagentoCartEstimateShippingMethod>>() {
            }.getType();
            List<MagentoCartEstimateShippingMethod> methods = gson.fromJson(responseEntity.getBody(), listType);
            if (methods == null) {
                throw new CartException(HttpStatus.NOT_FOUND, "Response is null");
            }
            return methods.stream().map(mapper::convertMagentoCartEstimateShippingMethodToGfCartEstimateShippingMethod).collect(Collectors.toList());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public GfCartShippingInformation setShippingInformation(String customerToken, GfCartAddressInformation addressInformation) throws CustomerException, CartException {
        String cartPaymentMethodsUrl = cartsUrl + "shipping-information/";
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            Map<String, Object> body = new HashMap<>();
            body.put("addressInformation", mapper.convertGfCartAddressInformationToMagentoCartAddressInformation(addressInformation));
            ResponseEntity<String> responseEntity = restClient.postForEntity(cartPaymentMethodsUrl, body, String.class, headers);

            MagentoCartShippingInformation cartCheckout = gson.fromJson(responseEntity.getBody(), MagentoCartShippingInformation.class);
            return mapper.convertMagentoCartShippingInformationToGfCartShippingInformation(cartCheckout);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<GfCartPaymentMethod> getPaymentMethods(String customerToken) throws CustomerException, CartException {
        String cartPaymentMethodsUrl = cartsUrl + "payment-methods/";
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            ResponseEntity<String> responseEntity = restClient.exchangeGet(cartPaymentMethodsUrl, String.class, headers);

            Type listType = new TypeToken<List<MagentoCartPaymentMethod>>() {
            }.getType();
            List<MagentoCartPaymentMethod> methods = gson.fromJson(responseEntity.getBody(), listType);
            if (methods == null) {
                throw new CartException(HttpStatus.NOT_FOUND, "Response is null");
            }
            return methods.stream().map(mapper::convertMagentoCartPaymentMethodToGfCartPaymentMethod).collect(Collectors.toList());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public String createOrder(String customerToken, GfCartPaymentInformation paymentInformation) throws CustomerException, CartException {
        String cartPaymentMethodsUrl = cartsUrl + "payment-information/";
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            MagentoCartPaymentInformation information = mapper.convertGfCartPaymentInformationToMagentoCartPaymentInformation(paymentInformation);
            ResponseEntity<String> responseEntity = restClient.postForEntity(cartPaymentMethodsUrl, information, String.class, headers);
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CartException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new CartException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
