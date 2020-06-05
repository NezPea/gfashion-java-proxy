package com.gfashion;

import com.gfashion.domain.cart.*;
import com.gfashion.domain.customer.GfCustomerLogin;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for the {@link GfCart} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCartItemDeleteIT {

    @LocalServerPort
    private int port;

    private int cartItemId;

    @Before
    public void setup() {
        RestAssured.port = port;
        Gson gson = new Gson();

        // Login to get customer token
        GfCustomerLogin params = new GfCustomerLogin("testli@test.com", "P@ssw0rd");
        Response response = given().header("Content-Type", "application/json")
                .body(gson.toJson(params))
                .post("/gfashion/v1/login/");
        String token = response.getBody().as(String.class);
        RestAssured.authentication = oauth2(token);

        // Get cart id
        response = get("/gfashion/v1/carts/");
        int cartId = response.jsonPath().getInt("id");

        // add cart item for get cart item id
        List<GFConfigurableItemOption> options = new ArrayList<>();
        options.add(new GFConfigurableItemOption("145", "5595"));
        options.add(new GFConfigurableItemOption("93", "5487"));

        GFExtensionAttributes attributes = new GFExtensionAttributes(options);
        GFProductOption option = new GFProductOption(attributes);

        GFCartItem cartItem = new GFCartItem();
        cartItem.setSku("WT09");
        cartItem.setQty(1);
        cartItem.setQuote_id(cartId);
        cartItem.setProduct_option(option);

        response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/");
        cartItemId = response.jsonPath().getInt("item_id");
    }

    @Test
    public void deleteCartItemReturnTrue() throws Exception {
        delete("/gfashion/v1/carts/items/{cartItemId}", cartItemId)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value());
    }
}
