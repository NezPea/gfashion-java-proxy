package com.gfashion.cart;

import com.gfashion.domain.cart.GfCartEstimateShippingMethod;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.any;

/**
 * Integration tests for the {@link GfCartEstimateShippingMethod} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCartPaymentMethodIT extends GfashionCartBaseIT {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
        super.setup();
    }

    @Test
    public void getEstimateShippingMethodsOnlyCountryId() throws Exception {
        get("/gfashion/v1/carts/payment-methods").then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("size()", any(Integer.class));
    }
}
