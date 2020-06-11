package com.gfashion;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsString;

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
        Response response = RestAssured.get("/gfashion/v1/homepage");
        response.then().assertThat().
                statusCode(200).
                content(
                        containsString("Miles"),
                        containsString("Trump"),
                        containsString("Pence")
                );
    }

    @Test
    public void getCategoriesByLevelRange() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/homepage/categories?fromLevel=1&toLevel=2");
        response.then().assertThat().
                statusCode(200).
                content(
                        containsString("男士"),
                        containsString("女士"),
                        containsString("其他"),
                        containsString("服装"),
                        containsString("配饰")
                );
    }

    @Test
    @Ignore
    public void getCustomerizedHomepageByCustomerIdShouldReturnOkDynamodb() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/dynamodb/homepage");
        response.then().assertThat().
                statusCode(200).
                content(
                        containsString("Miles"),
                        containsString("Trump"),
                        containsString("Pence")
                );
    }

    @Test
    @Ignore
    public void getCustomerizedHomepageByCustomerIdShouldReturnOkDynamodbChinese() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/dynamodb/homepage?locale=cn");
        response.then().assertThat().
                statusCode(200).
                content(
                        containsString("迈尔斯"),
                        containsString("川普"),
                        containsString("彭斯")
                );
    }
}
