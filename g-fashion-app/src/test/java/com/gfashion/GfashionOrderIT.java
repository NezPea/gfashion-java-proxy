package com.gfashion;

import com.gfashion.domain.sales.GfShipOrder;
import com.gfashion.domain.sales.GfShipmentItemCreation;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.gfashion.GfashionShipmentIT.adminAuth;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionOrderIT {
    @Value("${spring.profiles.active}")
    private String profile;

    @LocalServerPort
    private int port;
    Gson gson;

    @Before
    public void setup() {
        RestAssured.port = port;
        gson = new Gson();
        switch (profile) {
            case "dev":
                order_id = 96;
                order_item_id = 215;
                break;
            case "test":
            case "qa":
                order_id = 1;
                order_item_id = 1;
                break;
        }
    }

    private Integer order_id;
    private Integer order_item_id;

    /**
     * 如果执行失败，可能是订单的所有商品都已发货。
     */
    @Test
    public void shipOrder() throws Exception {
        List<GfShipmentItemCreation> items = new ArrayList();
        items.add(GfShipmentItemCreation.builder().orderItemId(order_item_id).qty(1).build());

        GfShipOrder gfShipOrder = new GfShipOrder();
        gfShipOrder.setItems(items);
        //
        RestAssured.given().header("Authorization", adminAuth)
                .request().contentType(ContentType.JSON)
                .body(gson.toJson(gfShipOrder))
                .post("/gfashion/v1/order/{orderId}/ship", order_id)
                .then().assertThat()
                .statusCode(200)
                .body("entityId", Matchers.greaterThan(0));//"67"
    }

    @Test
    public void getTracksByOrderId() throws Exception {
        /*RestAssured.given().param("searchCriteria", "order_id=50")
                .param("fields", "items[tracks]").when()*/
        RestAssured.given().header("Authorization", adminAuth)
                .get("/gfashion/v1/order/{orderId}/tracks", order_id).then().assertThat()
                .statusCode(200)
                .body("items.size()", Matchers.greaterThanOrEqualTo(0));
    }
}
