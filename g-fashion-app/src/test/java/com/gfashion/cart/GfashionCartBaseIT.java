package com.gfashion.cart;

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
        return response.jsonPath().getInt("itemId");
    }

    protected int addCartItem() {
        return addCartItem(getCartId());
    }

    protected void setShippingInformation() {
        GfCartAddressInformation params = createShippingInformationParams();
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/shipping-information");
    }

    protected int addCartItem(GfCartItem cartItem) {
        Response response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/");
        String json = response.getBody().asString();
        GfCartItem item = gson.fromJson(json, GfCartItem.class);
        return item.getItemId();
    }

    protected void deleteCartItem(int cartItemId) {
        if (cartItemId == 0) {
            return;
        }
        delete("/gfashion/v1/carts/items/{cartItemId}", cartItemId).then();
    }

    protected GfCartItem createCartItemParams() {
        return createCartItemParams(getCartId());
    }

    protected GfCartItem createCartItemParams(int cartId) {
        return createCartItemParams(cartId, "WT08", 1);
    }

    protected GfCartItem createCartItemParams(int cartId, String sku, int qty) {
        GfCartItem params = new GfCartItem();
        params.setSku(sku);
        params.setQty(qty);
        params.setQuoteId(cartId);
        params.setProductOption(createCartItemProductOption());
        return params;
    }

    protected GfCartItemProductOption createCartItemProductOption() {
        List<GfCartConfigurableItemOption> options = new ArrayList<>();
        GfCartConfigurableItemOption itemOption = new GfCartConfigurableItemOption();
        itemOption.setOptionId("144");
        itemOption.setOptionValue("5595");
        options.add(itemOption);
        itemOption = new GfCartConfigurableItemOption();
        itemOption.setOptionId("93");
        itemOption.setOptionValue("5487");
        options.add(itemOption);

        GfCartItemProductOptionExtensionAttributes attributes = new GfCartItemProductOptionExtensionAttributes();
        attributes.setConfigurableItemOptions(options);
        return new GfCartItemProductOption(attributes);
    }

    protected GfCartItemProductOption updateCartItemProductOption() {
        List<GfCartConfigurableItemOption> options = new ArrayList<>();
        GfCartConfigurableItemOption itemOption = new GfCartConfigurableItemOption();
        itemOption.setOptionId("144");
        itemOption.setOptionValue("5595");
        options.add(itemOption);
        itemOption = new GfCartConfigurableItemOption();
        itemOption.setOptionId("93");
        itemOption.setOptionValue("5484");
        options.add(itemOption);

        GfCartItemProductOptionExtensionAttributes attributes = new GfCartItemProductOptionExtensionAttributes();
        attributes.setConfigurableItemOptions(options);
        return new GfCartItemProductOption(attributes);
    }

    protected GfCartPaymentInfo createPaymentInformationParams() {
        GfCartPaymentMethod paymentMethod = new GfCartPaymentMethod();
        paymentMethod.setMethod("checkmo");

        GfCartPaymentInfo params = new GfCartPaymentInfo();
        params.setPaymentMethod(paymentMethod);
        params.setBillingAddress(createCartAddress());
        return params;
    }

    protected GfCartAddressInformation createShippingInformationParams() {
        return createShippingInformationParams("tablerate", "bestway");
    }

    protected GfCartAddressInformation createShippingInformationParams(String carrierCode, String methodCode) {
        GfCartAddressInformation params = new GfCartAddressInformation();
        params.setShippingAddress(createCartAddress());
        params.setShippingCarrierCode(carrierCode);
        params.setShippingMethodCode(methodCode);
        return params;
    }

    protected GfCartAddress createCartAddress() {
        List<String> street = new ArrayList<>();
        street.add("123 Oak Ave");

        GfCartAddress address = new GfCartAddress();
        address.setCountryId("US");
        address.setRegionId(43);
        address.setRegion("New York");
        address.setRegionCode("NY");
        address.setPostcode("10577");
        address.setCity("Purchase");
        address.setStreet(street);
        address.setFirstName("Jane");
        address.setLastName("Doe");
        address.setEmail("jdoe@example.com");
        address.setTelephone("512-555-1111");
        return address;
    }
}
