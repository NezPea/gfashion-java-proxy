package com.gfashion.customer;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerLogin;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;

/**
 * Integration tests for the {@link GfCustomer} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCustomerIT {

    @LocalServerPort
    private int port;

    @Value("${test.email}")
    private String email;

    @Value("${test.password}")
    private String password;

    private String token;

    @Before
    public void setup() {
        RestAssured.port = port;
        // Login to get customer token
        GfCustomerLogin params = new GfCustomerLogin(email, password);
        Response response = given().header("Content-Type", "application/json")
                .body(params)
                .post("/gfashion/v1/login/");
        token = response.getBody().as(String.class);
    }

    @Test
    public void getCustomerByIdShouldReturnCustomer() {
        Response response = RestAssured.given().header("Authorization", "Bearer " + token)
                .get("/gfashion/v1/customers/{customerId}", 3);
        response.then().assertThat().
                statusCode(200).
                body("id", any(Integer.class)).
                body("firstname", any(String.class));
    }

    @Test
    public void getCustomerByIdShouldReturnUnauthorizedException() {
        Response response = RestAssured.given().header("Authorization", "Bearer " + token)
                .get("/gfashion/v1/customers/{customerId}", 1);
        response.then().assertThat().
                statusCode(401);
    }

    @Test
    public void getCustomerVerificationCodeWithBadAuthShouldReturnUnauthorizedException() {
        Response response = RestAssured.given().header("Authorization", "Bearer " + token)
                .body("{\"email\": \"andrewluo.us@gmail.com\"}").post("/gfashion/v1/customers/{customerId}/getVerificationCode", 22);
        response.then().assertThat().
                statusCode(401);
    }

    @Test
    @Ignore
    public void createCustomerByIdShouldReturnBadRequestException() {
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
