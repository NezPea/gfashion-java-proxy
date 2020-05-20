package com.gfashion;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;

/**
 * Integration tests for the {@link com.gfashion.domain.GfDesigner} REST controller.
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
        ;
    }
}
