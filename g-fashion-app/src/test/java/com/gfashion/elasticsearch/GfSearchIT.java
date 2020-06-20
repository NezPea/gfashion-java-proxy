package com.gfashion.elasticsearch;

import com.gfashion.data.repository.elasticsearch.model.EsDesigner;
import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.data.repository.elasticsearch.repostory.EsDesignerRepository;
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
import org.springframework.data.elasticsearch.core.completion.Completion;
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

    @Resource
    private EsDesignerRepository designerRepository;

    @Autowired
    protected Gson gson;

    @Before
    public void setup() {
        RestAssured.port = port;

        mockProduct();
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
                .body("success", is(true),"products.total", greaterThan(0), "products.pageNo", is(1));
    }

    @Test
    public void searchProductByCategory() throws Exception {
        GfProductSearchRequest request = new GfProductSearchRequest();
        request.setCategoryId(822L);

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(request))
                .post("/gfashion/v1/search")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("success", is(true), "products.total", greaterThan(0), "products.pageNo", is(1));
    }

    @Test
    public void suggestDesigner() throws Exception {
        GfProductSearchRequest request = new GfProductSearchRequest();
        request.setKeyword("李");

        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(request))
                .post("/gfashion/v1/designer")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("success", is(true), "data.size()", greaterThan(0));
    }

    @After
    public void clearTestData() throws Exception {
        // Catch exception because there is no permission for refreshing
        try {
            productRepository.deleteById("1000");
            productRepository.deleteById("1001");

            designerRepository.deleteById("100");
            designerRepository.deleteById("101");
            designerRepository.deleteById("102");
        } catch (Exception e) {

        }
    }

    private void mockProduct() {
        // Catch exception because there is no permission for refreshing
        try {
            EsProduct product = EsProduct.builder()
                    .id("100")
                    .price(10000)
                    .desEn("Slim-fit plain-woven stretch wool trousers in black. Low-rise. Five-pocket styling. Belt loops at waistband. Central creases at front and back. Zip-fly. Partially lined.")
                    .nameEn("Black Wool Herris Trousers")
                    .smallPic("https://img.ssensemedia.com/image/upload/b_white/c_scale,h_820/f_auto,dpr_2.0/201020M205048_1.jpg")
                    .categories(new Long[]{790L, 794L, 819L, 822L})
                    .categoryId(822L)
                    .topCategoryId(790L)
                    .sale(1)
                    .designerId(101L)
                    .sizeList(new String[]{"S", "M", "L", "XL", "XXL"})
                    .build();

            EsProduct product1 = EsProduct.builder()
                    .id("101")
                    .price(15000)
                    .desEn("Relaxed-fit technical twill cargo pants in black. Mid-rise. Four-pocket styling. Belt loops at partially elasticized waistband. Darts at front, back, and legs. Zippered pocket at outseams. Elasticized cuffs. Zip-fly. Tonal hardware.")
                    .nameEn("Black Dimensional Out Pocket Cargo Pants")
                    .smallPic("https://img.ssensemedia.com/image/upload/b_white/c_scale,h_820/f_auto,dpr_2.0/201020M213021_1.jpg")
                    .categories(new Long[]{790L, 794L, 819L, 823L})
                    .categoryId(823L)
                    .topCategoryId(790L)
                    .sale(0)
                    .designerId(102L)
                    .sizeList(new String[]{"36", "37", "38"})
                    .build();

            List<EsProduct> products = new ArrayList<>();
            products.add(product);
            products.add(product1);
            productRepository.saveAll(products);
        } catch (Exception e) {

        }
    }

    public void mockDesigner() {
        // Catch exception because there is no permission for refreshing
        try {
            EsDesigner designer = EsDesigner.builder().id(100L).nameZh("李世民").suggest(new Completion(new String[]{"李世明"})).build();
            EsDesigner designer1 = EsDesigner.builder().id(101L).nameEn("Nirvana").suggest(new Completion(new String[]{"Nirvana"})).build();
            EsDesigner designer2 = EsDesigner.builder().id(102L).nameEn("Nevermind Never").suggest(new Completion(new String[]{"Nevermind Never"})).build();
            List<EsDesigner> designers = new ArrayList<>();
            designers.add(designer);
            designers.add(designer1);
            designers.add(designer2);
            designerRepository.saveAll(designers);
        } catch (Exception e) {

        }
    }

}

