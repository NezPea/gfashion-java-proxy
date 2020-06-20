package com.gfashion.cart;

import com.gfashion.domain.cart.GfCartAdyenPaymentMethod;
import com.gfashion.domain.cart.GfCartAdyenPaymentParam;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;

/**
 * Integration tests for the {@link GfCartAdyenPaymentMethod} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCartAdyenPaymentMethodIT extends GfashionCartBaseIT {

    @LocalServerPort
    private int port;

    private Integer cartId;
    private int cartItemId;

    @Before
    public void setup() {
        RestAssured.port = port;
        super.setup();

        cartId = getCartId();
        cartItemId = addCartItem(cartId);
    }

    @Test
    public void getAdyenPaymentMethods() throws Exception {
        GfCartAdyenPaymentParam param = new GfCartAdyenPaymentParam(String.valueOf(cartId), createCartAddress());
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(param))
                .post("/gfashion/v1/carts/retrieve-adyen-payment-methods")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("size()", any(Integer.class));
    }

    @After
    public void clearTestData() throws Exception {
        deleteCartItem(cartItemId);
    }
}
