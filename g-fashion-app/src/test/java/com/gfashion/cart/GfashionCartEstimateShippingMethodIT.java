package com.gfashion.cart;

import com.gfashion.domain.cart.GfCartAddress;
import com.gfashion.domain.cart.GfCartEstimateShippingMethod;
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
import static org.hamcrest.Matchers.is;

/**
 * Integration tests for the {@link GfCartEstimateShippingMethod} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCartEstimateShippingMethodIT extends GfashionCartBaseIT {

    @LocalServerPort
    private int port;

    private int cartItemId;

    @Before
    public void setup() {
        RestAssured.port = port;
        super.setup();

        cartItemId = addCartItem();
    }

    @Test
    public void getEstimateShippingMethodsOnlyCountryId() throws Exception {
        GfCartAddress address = new GfCartAddress();
        address.setCountryId("US");

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(address))
                .post("/gfashion/v1/carts/estimate-shipping")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2));
    }

    @Test
    public void getEstimateShippingMethodsWithoutCountryId() throws Exception {
        GfCartAddress address = new GfCartAddress();
        address.setRegionId(43);
        address.setPostcode("10577");

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(address))
                .post("/gfashion/v1/carts/estimate-shipping")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }

    @Test
    public void getEstimateShippingMethodsWithRegionId() throws Exception {
        GfCartAddress address = new GfCartAddress();
        address.setCountryId("US");
        address.setRegionId(43);

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(address))
                .post("/gfashion/v1/carts/estimate-shipping")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2));
    }

    @Test
    public void getEstimateShippingMethodsWithPostcode() throws Exception {
        GfCartAddress address = new GfCartAddress();
        address.setCountryId("US");
        address.setPostcode("10577");

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(address))
                .post("/gfashion/v1/carts/estimate-shipping")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("size()", any(Integer.class));
    }

    @After
    public void clearTestData() throws Exception {
        deleteCartItem(cartItemId);
    }
}
