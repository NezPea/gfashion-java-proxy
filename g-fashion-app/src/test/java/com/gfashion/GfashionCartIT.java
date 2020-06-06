package com.gfashion;

import com.gfashion.domain.cart.*;
import com.gfashion.domain.customer.GfCustomerLogin;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
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
public class GfashionCartIT {

    @LocalServerPort
    private int port;

    private Gson gson;
    private int cartId;
    private int cartItemId;

    @Before
    public void setup() {
        RestAssured.port = port;
        gson = new Gson();

        // Login to get customer token
        GfCustomerLogin params = new GfCustomerLogin("testli@test.com", "P@ssw0rd");
        Response response = given().header("Content-Type", "application/json")
                .body(gson.toJson(params))
                .post("/gfashion/v1/login/");
        String token = response.getBody().as(String.class);
        RestAssured.authentication = oauth2(token);

        // Get cart id
        response = get("/gfashion/v1/carts/");
        cartId = response.jsonPath().getInt("id");

        // add cart item for get cart item id
        List<GfConfigurableItemOption> options = new ArrayList<>();
        options.add(new GfConfigurableItemOption("145", "5595"));
        options.add(new GfConfigurableItemOption("93", "5487"));

        GfExtensionAttributes attributes = new GfExtensionAttributes(options);
        GfProductOption option = new GfProductOption(attributes);

        GfCartItem cartItem = new GfCartItem();
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
    public void getCartReturnCart() throws Exception {
        get("/gfashion/v1/carts/").then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(101))
                .body("items", any(List.class))
                .body("items_count", any(Integer.class))
                .body("items_qty", any(Integer.class));
    }

    @Test
    public void getCartItemsReturnCartItems() throws Exception {
        get("/gfashion/v1/carts/items/").then().assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void addCartItemWithErrorSku() throws Exception {
        List<GfConfigurableItemOption> options = new ArrayList<>();
        options.add(new GfConfigurableItemOption("145", "5595"));
        options.add(new GfConfigurableItemOption("93", "5487"));

        GfExtensionAttributes attributes = new GfExtensionAttributes(options);
        GfProductOption option = new GfProductOption(attributes);

        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("Error");
        cartItem.setQty(1);
        cartItem.setQuote_id(cartId);
        cartItem.setProduct_option(option);

        System.out.println(gson.toJson(cartItem));

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void addCartItemWithoutCartId() throws Exception {
        List<GfConfigurableItemOption> options = new ArrayList<>();
        options.add(new GfConfigurableItemOption("145", "5595"));
        options.add(new GfConfigurableItemOption("93", "5487"));

        GfExtensionAttributes attributes = new GfExtensionAttributes(options);
        GfProductOption option = new GfProductOption(attributes);

        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("WT09");
        cartItem.setQty(1);
        cartItem.setProduct_option(option);

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void addCartItemWithoutOptionsSku() throws Exception {
        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("Error");
        cartItem.setQty(1);
        cartItem.setQuote_id(cartId);

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateCartItemReturnCartItem() throws Exception {
        List<GfConfigurableItemOption> options = new ArrayList<>();
        options.add(new GfConfigurableItemOption("145", "5595"));
        options.add(new GfConfigurableItemOption("93", "5484"));

        GfExtensionAttributes attributes = new GfExtensionAttributes(options);
        GfProductOption option = new GfProductOption(attributes);

        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("WT09");
        cartItem.setQty(2);
        cartItem.setQuote_id(cartId);
        cartItem.setProduct_option(option);

        Response response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .put("/gfashion/v1/carts/items/{cartItemId}", cartItemId);
        response.then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("item_id", any(Integer.class))
                .body("sku", startsWith("WT09"))
                .body("qty", any(Integer.class))
                .body("name", any(String.class))
                .body("price", any(Integer.class))
                .body("product_type", any(String.class))
                .body("quote_id", equalTo(cartId));

        List<Object> list = response.jsonPath().getList("product_option.extension_attributes.configurable_item_options");
        assertThat(list.size(), is(2));

        cartItemId = response.jsonPath().getInt("item_id");
    }

    @Test
    public void updateCartItemReturnNotFound() throws Exception {
        List<GfConfigurableItemOption> options = new ArrayList<>();
        options.add(new GfConfigurableItemOption("145", "5595"));
        options.add(new GfConfigurableItemOption("93", "5484"));

        GfExtensionAttributes attributes = new GfExtensionAttributes(options);
        GfProductOption option = new GfProductOption(attributes);

        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("WT09");
        cartItem.setQty(2);
        cartItem.setQuote_id(cartId);
        cartItem.setProduct_option(option);

        System.out.println(gson.toJson(cartItem));
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .put("/gfashion/v1/carts/items/{cartItemId}", -1)
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteCartItemReturnNotFound() throws Exception {
        delete("/gfashion/v1/carts/items/{cartItemId}", -1)
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @After
    public void deleteCartItem() throws Exception {
        delete("/gfashion/v1/carts/items/{cartItemId}", cartItemId).then();
    }
}
