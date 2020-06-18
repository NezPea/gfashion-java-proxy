package com.gfashion.customer;

import com.gfashion.domain.customer.GfCustomerLogin;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

/**
 * Integration tests for the {@link GfCustomerLogin} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionCustomerLoginIT {

    @LocalServerPort
    private int port;

    @Autowired
    protected Gson gson;

    @Value("${test.email}")
    private String email;
    @Value("${test.password}")
    private String password;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void customerLoginSuccess() throws Exception {
        GfCustomerLogin params = new GfCustomerLogin(email, password);
        given().header("Content-Type", "application/json")
                .body(gson.toJson(params))
                .post("/gfashion/v1/login/")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Ignore
    public void customerLoginFailureWithErrorUsername() throws Exception {
        GfCustomerLogin params = new GfCustomerLogin("errorUsername", password);
        given().header("Content-Type", "application/json")
                .body(gson.toJson(params))
                .post("/gfashion/v1/login/")
                .then().assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @Ignore
    public void customerLoginFailureWithErrorPassword() throws Exception {
        GfCustomerLogin params = new GfCustomerLogin(email, "errorPassword");
        given().header("Content-Type", "application/json")
                .body(gson.toJson(params))
                .post("/gfashion/v1/login/")
                .then().assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
