package com.gfashion;

import com.gfashion.domain.cart.GfCart;
import com.gfashion.domain.cart.GfCartItem;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for the {@link GfCart} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCartIT extends GfashionCartBaseIT {

    @LocalServerPort
    private int port;

    private int cartItemId;

    @Before
    public void setup() {
        RestAssured.port = port;
        super.setup();
    }

    @Test
    public void getCartReturnCart() throws Exception {
        get("/gfashion/v1/carts/").then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", any(Integer.class))
                .body("items", any(List.class))
                .body("itemsCount", any(Integer.class))
                .body("itemsQty", any(Integer.class));
    }

    @Test
    public void getCartItemsReturnCartItems() throws Exception {
        get("/gfashion/v1/carts/items/").then().assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void addCartItemReturnCartItem() throws Exception {
        int cartId = getCartId();
        GfCartItem cartItem = createCartItemParams(cartId);

        Response response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/");

        response.then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("itemId", any(Integer.class))
                .body("sku", startsWith("WT09"))
                .body("qty", any(Integer.class))
                .body("name", any(String.class))
                .body("price", any(Integer.class))
                .body("productType", any(String.class))
                .body("quoteId", equalTo(cartId));

        List<Object> list = response.jsonPath().getList("productOption.extensionAttributes.configurableItemOptions");
        assertThat(list.size(), is(2));

        cartItemId = response.jsonPath().getInt("itemId");
    }

    @Test
    @Ignore
    public void addCartItemWithErrorSku() throws Exception {
        GfCartItem cartItem = createCartItemParams();
        cartItem.setSku(null);

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void addCartItemWithoutCartId() throws Exception {
        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("WT09");
        cartItem.setQty(1);
        cartItem.setProductOption(createCartItemProductOption());

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void addCartItemWithoutOptionsSku() throws Exception {
        int cartId = getCartId();

        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("Error");
        cartItem.setQty(1);
        cartItem.setQuoteId(cartId);

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .post("/gfashion/v1/carts/items/")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateCartItemReturnCartItem() throws Exception {
        int cartId = getCartId();
        cartItemId = addCartItem(cartId);

        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("WT09");
        cartItem.setQty(2);
        cartItem.setQuoteId(cartId);
        cartItem.setProductOption(updateCartItemProductOption());

        Response response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .put("/gfashion/v1/carts/items/{cartItemId}", cartItemId);
        response.then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("itemId", any(Integer.class))
                .body("sku", startsWith("WT09"))
                .body("qty", any(Integer.class))
                .body("name", any(String.class))
                .body("price", any(Integer.class))
                .body("productType", any(String.class))
                .body("quoteId", equalTo(cartId));

        List<Object> list = response.jsonPath().getList("productOption.extensionAttributes.configurableItemOptions");
        assertThat(list.size(), is(2));

        cartItemId = response.jsonPath().getInt("itemId");
    }

    @Test
    public void updateCartItemReturnNotFound() throws Exception {
        int cartId = getCartId();

        GfCartItem cartItem = new GfCartItem();
        cartItem.setSku("WT09");
        cartItem.setQty(2);
        cartItem.setQuoteId(cartId);
        cartItem.setProductOption(updateCartItemProductOption());

        System.out.println(gson.toJson(cartItem));
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(cartItem))
                .put("/gfashion/v1/carts/items/{cartItemId}", -1)
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteCartItemReturnTrue() throws Exception {
        int cartItemId = addCartItem();

        delete("/gfashion/v1/carts/items/{cartItemId}", cartItemId)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deleteCartItemReturnNotFound() throws Exception {
        delete("/gfashion/v1/carts/items/{cartItemId}", -1)
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @After
    public void clearTestData() throws Exception {
        deleteCartItem(cartItemId);
    }
}
