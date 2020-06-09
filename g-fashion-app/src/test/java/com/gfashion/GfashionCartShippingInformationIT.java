package com.gfashion;

import com.gfashion.domain.cart.GfCartAddressInformation;
import com.gfashion.domain.cart.GfCartShippingInformation;
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

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for the {@link GfCartShippingInformation} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCartShippingInformationIT extends GfashionCartBaseIT {

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
    public void setCartShippingInfo() throws Exception {
        GfCartAddressInformation params = createShippingInformationParams();
        Response response = given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/shipping-information");

        response.then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("totals.itemsQty", any(Integer.class))
                .body("totals.items.size()", any(Integer.class))
                .body("totals.totalSegments.size()", any(Integer.class));

        List<Map<String, Object>> paymentMethods = response.jsonPath().getList("paymentMethods");
        assertThat(paymentMethods.size(), any(Integer.class));
    }

    @Test
    public void setCartShippingInfoWithoutShippingAddress() throws Exception {
        GfCartAddressInformation params = createShippingInformationParams();
        params.setShippingAddress(null);
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/shipping-information")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void setCartShippingInfoWithoutShippingCarrierCode() throws Exception {
        GfCartAddressInformation params = createShippingInformationParams();
        params.setShippingCarrierCode(null);
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/shipping-information")
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void setCartShippingInfoWithoutShippingMethodCode() throws Exception {
        GfCartAddressInformation params = createShippingInformationParams();
        params.setShippingCarrierCode(null);
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(params))
                .post("/gfashion/v1/carts/shipping-information")
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @After
    public void clearTestData() throws Exception {
        deleteCartItem(cartItemId);
    }
}
