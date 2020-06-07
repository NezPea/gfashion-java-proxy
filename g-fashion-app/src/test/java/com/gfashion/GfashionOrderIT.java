package com.gfashion;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.GfShipmentItem;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionOrderIT {

    @LocalServerPort
    private int port;
    Gson gson;

    @Before
    public void setup() {
        RestAssured.port = port;
        gson = new Gson();
    }

    @Test
    public void createShipment() throws Exception {
        List<GfShipmentItem> items = new ArrayList();
        items.add(GfShipmentItem.builder().order_item_id(153).qty(1).build());

        GfShipment gfShipment = new GfShipment();
        gfShipment.setItems(items);
        //
        RestAssured.given().response().contentType(ContentType.TEXT).request().contentType(ContentType.JSON)
                .body(gson.toJson(gfShipment))
                .post("/gfashion/v1/order/{orderId}/ship", 67)
                .then().assertThat()
                .statusCode(200)
                .body(Matchers.matchesRegex("\"\\d+\""));//"67"
    }

    @Test
    public void queryShipments() throws Exception {
        RestAssured.given().param("searchCriteria", "order_id=50")
                .param("fields", "items[tracks]").when()
                .get("/gfashion/v1/shipments").then().assertThat()
                .statusCode(200)
                .body("items.size()", greaterThanOrEqualTo(0));
    }
}
