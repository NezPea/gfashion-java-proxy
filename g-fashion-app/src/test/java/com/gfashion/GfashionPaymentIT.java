package com.gfashion;

import com.gfashion.domain.payment.GfShipping;
import com.gfashion.domain.payment.ShippingAddress;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionPaymentIT {
    @Autowired
    protected Gson gson;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void getPaymentMethods() throws Exception {
        GfShipping gfShipping = new GfShipping();
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setCustomerAddressId("1");
        shippingAddress.setCountryId("NL");
        shippingAddress.setRegionCode("null");
        shippingAddress.setRegion("null");
        shippingAddress.setCustomerId("1");
        List<String> streets = new ArrayList<>();
        streets.add("Neherkade 1 XI");
        shippingAddress.setStreet(streets);
        shippingAddress.setTelephone("0612345678");
        shippingAddress.setPostcode("2521VA");
        shippingAddress.setCity("Gravenhage");
        shippingAddress.setFirstname("Test");
        shippingAddress.setLastname("Test");
        gfShipping.setShippingAddress(shippingAddress);
        gfShipping.setCustomerToken("bj4gs2gpr905wy971s2jdhhqzu8sj6lc");

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(gfShipping))
                .post("/gfashion/v1/carts/mine/retrieve-adyen-payment-methods")
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getPaymentStatus() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/payment/{orderId}/status", "1");
        response.then().assertThat()
                .statusCode(400);
    }

    @Test
    public void getOriginKey() throws Exception {
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(""))
                .post("/gfashion/v1/adyen/originKey")
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
