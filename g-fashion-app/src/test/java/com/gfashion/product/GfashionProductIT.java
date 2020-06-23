package com.gfashion.product;

import com.gfashion.domain.product.GfProduct;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

/**
 * Integration tests for the {@link GfProduct} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class GfashionProductIT {


    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void getProductByIdShouldReturnProduct() throws Exception {

        Response response = RestAssured.get("/gfashion/v1/detail/products?productId={productId}","1");
        response.then().assertThat()
                .statusCode(200)
                .body("id", equalTo("1"))
                .body("designerId",equalTo("1001"))
                  ;
    }




//    @Test
//    public void getProductByIdShouldReturnProduct() throws Exception {
//        Response response = RestAssured.get("/gfashion/v1/products/{sku}", "WJ12-M-Black");
//        response.then().assertThat()
//                .statusCode(200)
//                .body("id", equalTo(1394))
//                .body("sku", equalTo("WJ12-M-Black"))
//                .body("attribute_set_id", equalTo(9))
//                .body("price", equalTo(77));
//    }

    @Test
    public void getProductBySkuShouldReturnNotFoundException() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/products/{sku}", -1);
        response.then().assertThat().
                statusCode(404);
    }

    @Test
    @Ignore
    public void searchChannelProductsShouldReturnProducts() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/gfashion/v1/channelProducts");
        request.setQueryString("category_id,23,eq&price,150,lt&color,5487_5477_5485,eq&pageSize=20&currentPage=1&sortField=id&sortDirection=asc&price,150,lt");
        String url = request.getRequestURL() + "?" + request.getQueryString();

        Response response = RestAssured.get(url);
        response.then().assertThat().
                statusCode(200);
    }



}
