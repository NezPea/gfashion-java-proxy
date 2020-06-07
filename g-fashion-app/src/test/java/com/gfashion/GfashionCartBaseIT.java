package com.gfashion;

import com.gfashion.domain.cart.*;
import com.gfashion.domain.customer.GfCustomerLogin;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GfashionCartBaseIT {

    @Autowired
    protected Gson gson;

    @Value("${test.email}")
    private String email;
    @Value("${test.password}")
    private String password;

    protected void setup() {
        // Login to get customer token
        GfCustomerLogin params = new GfCustomerLogin(email, password);
        Response response = given().header("Content-Type", "application/json")
                .body(gson.toJson(params))
                .post("/gfashion/v1/login/");
        String token = response.getBody().as(String.class);
        RestAssured.authentication = oauth2(token);
    }

    protected int getCartId() {
        // Get cart id
        Response response = get("/gfashion/v1/carts/");
        return response.jsonPath().getInt("id");
    }

    protected int addCartItem(int cartId) {
        // add cart item for get cart item id
        GfCartItem params = createCartItemParams(cartId);
        Response response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/items/");
        return response.jsonPath().getInt("item_id");
    }

    protected int addCartItem() {
        return addCartItem(getCartId());
    }

    public void deleteCartItem(int cartItemId) {
        if (cartItemId == 0) {
            return;
        }
        delete("/gfashion/v1/carts/items/{cartItemId}", cartItemId).then();
    }

    private GfCartItem createCartItemParams(int cartId) {
        List<GfConfigurableItemOption> options = new ArrayList<>();
        options.add(new GfConfigurableItemOption("145", "5595"));
        options.add(new GfConfigurableItemOption("93", "5487"));

        GfExtensionAttributes attributes = new GfExtensionAttributes(options);
        GfProductOption option = new GfProductOption(attributes);

        GfCartItem params = new GfCartItem();
        params.setSku("WT09");
        params.setQty(1);
        params.setQuote_id(cartId);
        params.setProduct_option(option);
        return params;
    }

    protected GfCartAddressInformation createShippingInformationParams() {
        GfCartAddressInformation params = new GfCartAddressInformation();
        params.setShipping_address(createCartAddress());
        params.setShipping_carrier_code("tablerate");
        params.setShipping_method_code("bestway");
        return params;
    }

    private GfCartAddress createCartAddress() {
        List<String> street = new ArrayList<>();
        street.add("123 Oak Ave");

        GfCartAddress address = new GfCartAddress();
        address.setCountry_id("US");
        address.setRegion_id(43);
        address.setRegion("New York");
        address.setRegion_code("NY");
        address.setPostcode("10577");
        address.setCity("Purchase");
        address.setStreet(street);
        address.setFirstname("Jane");
        address.setLastname("Doe");
        address.setEmail("jdoe@example.com");
        address.setTelephone("512-555-1111");
        return address;
    }
}
