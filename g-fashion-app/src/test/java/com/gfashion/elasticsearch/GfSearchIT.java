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
            EsProduct product = new EsProduct();
            product.setId("1000");
            product.setBrandId("100");
            product.setPrice(10000);
            product.setBrandName("channel");
            product.setBrief("Slim-fit plain-woven stretch wool trousers in black. Low-rise. Five-pocket styling. Belt loops at waistband. Central creases at front and back. Zip-fly. Partially lined.");
            product.setName("Black Wool Herris Trousers");
            product.setCategories(new String[]{"clothing", "trousers"});
            product.setGender("F");
            product.setSale(1);
            product.setDesignerId("1003239");
            product.setSize("XXL");
            product.setLanguage("en");

            EsProduct product1 = new EsProduct();
            product1.setId("1001");
            product1.setBrandId("101");
            product1.setPrice(15000);
            product1.setBrandName("adidass");
            product1.setBrief("Relaxed-fit technical twill cargo pants in black. Mid-rise. Four-pocket styling. Belt loops at partially elasticized waistband. Darts at front, back, and legs. Zippered pocket at outseams. Elasticized cuffs. Zip-fly. Tonal hardware.");
            product1.setName("Black Dimensional Out Pocket Cargo Pants");
            product1.setCategories(new String[]{"bags", "shoes"});
            product1.setGender("F");
            product1.setSale(0);
            product1.setDesignerId("09a88ser2");
            product1.setSize("36");
            product1.setLanguage("cn");

            List<EsProduct> products = new ArrayList<>();
            products.add(product);
            products.add(product1);
            productRepository.saveAll(products);
        } catch (Exception e) {

        }
    }
}

