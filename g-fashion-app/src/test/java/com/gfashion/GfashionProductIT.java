package com.gfashion;

import com.gfashion.domain.product.GfProduct;
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
 * Integration tests for the {@link GfProduct} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionProductIT {


    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void getProductByIdShouldReturnProduct() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/products/{sku}", "WJ12-M-Black");
        response.then().assertThat()
                .statusCode(200)
                .body("id", equalTo(1393))
                .body("sku", equalTo("WJ12-M-Black"))
                .body("attribute_set_id", equalTo(9))
                .body("price", equalTo(77));
    }

    @Test
    public void getProductBySkuShouldReturnNotFoundException() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/products/{sku}", -1);
        response.then().assertThat().
                statusCode(404);
    }

    @Test
    public void searchChannelProductsShouldReturnProducts() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/channelProducts/{query}", "?searchCriteria[filter_groups][0][filters][0][field]=category_id&searchCriteria[filter_groups][0][filters][0][value]=23&searchCriteria[filter_groups][0][filters][0][condition_type]=eq&searchCriteria[filter_groups][3][filters][0][field]=color&searchCriteria[filter_groups][3][filters][0][value]=5485&searchCriteria[filter_groups][3][filters][0][condition_type]=eq&searchCriteria[pageSize]=20&searchCriteria[currentPage]=0&searchCriteria[sortOrders][0][field]=price&searchCriteria[sortOrders][0][direction]=desc");
        response.then().assertThat().
                statusCode(200);
    }
}
