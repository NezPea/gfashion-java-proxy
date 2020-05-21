package com.gfashion;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GFashionHomepageIT {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void getCustomerizedHomepageByCustomerIdShouldReturnOk() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/homepage/{customerId}", 4);
        response.then().assertThat().
                statusCode(200).
                body("id", equalTo(4)).content(
                        containsString("recommendedProducts"),
                        containsString("recommendedDesigners"),
                        containsString("recommendedBrands"),
                        containsString("followingBrands"),
                        containsString("followingDesigners"),
                        containsString("Raffaello")
                );
    }

    @Test
    public void getCustomerizedHomepageByCustomerIdShouldReturnNotFound() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/homepage/{customerId}", 1);
        response.then().assertThat().
                statusCode(404);
    }
}
