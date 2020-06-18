package com.gfashion;

import com.gfashion.domain.cart.GfCartPaymentInformation;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for the {@link GfCartPaymentInformation} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCartPaymentInformationIT extends GfashionCartBaseIT {

    @LocalServerPort
    private int port;

    private int cartItemId;

    @Before
    public void setup() {
        RestAssured.port = port;
        super.setup();

        cartItemId = addCartItem();
        setShippingInformation();
    }

    @Test
    public void createOrder() throws Exception {
        GfCartPaymentInformation params = createPaymentInformationParams();
        Response response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/payment-information");

        response.then().assertThat().statusCode(HttpStatus.OK.value());

        String orderId = response.getBody().as(String.class);
        assertThat(orderId, is(any(String.class)));
    }

    @Test
    public void createOrderWithoutPaymentMethod() throws Exception {
        GfCartPaymentInformation params = createPaymentInformationParams();
        params.setPaymentMethod(null);
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/payment-information")
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Ignore
    public void createOrderWithoutBillingAddress() throws Exception {
        GfCartPaymentInformation params = createPaymentInformationParams();
        params.setBillingAddress(null);
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/payment-information")
                .then().assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @After
    public void clearTestData() throws Exception {
        deleteCartItem(cartItemId);
    }
}
