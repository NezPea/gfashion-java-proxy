package com.gfashion.elasticsearch;

import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.data.repository.elasticsearch.repostory.EsProductRepository;
import com.gfashion.domain.cart.GfCartEstimateShippingMethod;
import com.gfashion.domain.elasticsearch.GfProductSearchRequest;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Integration tests for the {@link GfCartEstimateShippingMethod} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfSearchIT {

    @LocalServerPort
    private int port;

    @Resource
    private EsProductRepository productRepository;

    @Autowired
    protected Gson gson;

    @Before
    public void setup() {
        RestAssured.port = port;

        mockData();
    }

    @Test
    public void searchProductByKeyword() throws Exception {
        GfProductSearchRequest request = new GfProductSearchRequest();
        request.setKeyword("trousers");

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(request))
                .post("/gfashion/v1/search")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("total", greaterThan(0), "pageNo", is(1));
    }

    @After
    public void clearTestData() throws Exception {
        try {
            productRepository.deleteById("1000");
            productRepository.deleteById("1001");
        } catch (Exception e) {

        }
    }

    private void mockData() {
        try {
            EsProduct product = EsProduct.builder()
                    .id("1000")
                    .brandId("100")
                    .price(10000)
                    .brandName("channel")
                    .brief("Slim-fit plain-woven stretch wool trousers in black. Low-rise. Five-pocket styling. Belt loops at waistband. Central creases at front and back. Zip-fly. Partially lined.")
                    .name("Black Wool Herris Trousers")
                    .categories(new Integer[]{100, 1001})
                    .gender("F")
                    .sale(1)
                    .designerId(1003239)
                    .size("XXL")
                    .language("en")
                    .build();

            EsProduct product1 = EsProduct.builder()
                    .id("1001")
                    .brandId("101")
                    .price(15000)
                    .brandName("adidass")
                    .brief("Relaxed-fit technical twill cargo pants in black. Mid-rise. Four-pocket styling. Belt loops at partially elasticized waistband. Darts at front, back, and legs. Zippered pocket at outseams. Elasticized cuffs. Zip-fly. Tonal hardware.")
                    .name("Black Dimensional Out Pocket Cargo Pants")
                    .categories(new Integer[]{101, 1011})
                    .gender("F")
                    .sale(0)
                    .designerId(92)
                    .size("36")
                    .language("cn")
                    .build();

            List<EsProduct> products = new ArrayList<>();
            products.add(product);
            products.add(product1);
            productRepository.saveAll(products);
        } catch (Exception e) {

        }
    }
}

