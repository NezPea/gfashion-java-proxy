package com.gfashion;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;

/**
 * Integration tests for the {@link GfCustomer} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCustomerIT {


    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void getCustomerByIdShouldReturnCustomer() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/customers/{customerId}", 4);
        response.then().assertThat().
                statusCode(200).
                body("id", equalTo(4)).
                body("firstname", equalTo("Allen"));
    }

    @Test
    public void getCustomerByIdShouldReturnNotFoundException() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/customers/{customerId}", -1);
        response.then().assertThat().
                statusCode(404);
    }

    @Test
    public void createCustomerByIdShouldReturnBadRequestException() throws Exception {
        GfCustomerRegistration newCustomerRegistration = new GfCustomerRegistration();
        GfCustomer newCustomer = new GfCustomer();
        newCustomerRegistration.setCustomer(newCustomer);

        Gson gson = new Gson();
        Response response = RestAssured.given().header("Content-Type", "application/json")
                .body(gson.toJson(newCustomerRegistration)).post("/gfashion/v1/customers");
        response.then().assertThat().
                statusCode(400);
    }
}
