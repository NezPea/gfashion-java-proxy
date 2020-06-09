package com.gfashion;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.GfShipmentItem;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionShipmentIT {

    @LocalServerPort
    private int port;
    Gson gson;

    @Before
    public void setup() {
        RestAssured.port = port;
        gson = new Gson();
    }

    @Test
    public void getShipmentById() throws Exception {
        Response response = RestAssured.get("/gfashion/v1/shipment/{shipmentId}", shipment_id);
        response.then().assertThat()
                .statusCode(200)
                .body("entity_id", equalTo(shipment_id));
    }

    @Test
    public void updateShipment() throws Exception {
        String value = "wayne";
        GfShipment gfShipment = new GfShipment();
        gfShipment.setOrder_id(order_id);
        List<GfShipmentItem> items = new ArrayList();
        items.add(GfShipmentItem.builder().order_item_id(order_item_id).description(value).build());
        gfShipment.setItems(items);
        //
        given().header("Content-Type", ContentType.JSON)
                .body(gson.toJson(gfShipment))
                .post("/gfashion/v1/shipment")
                .then().assertThat()
                .statusCode(200)
                .body("items[0].description", equalTo(value));
    }

    @Value("${test.GfashionShipmentIT.order_id}")
    private Integer order_id;

    @Value("${test.GfashionShipmentIT.order_item_id}")
    private Integer order_item_id;

    @Value("${test.GfashionShipmentIT.shipment_id}")
    private Integer shipment_id;

    @Test
    public void queryShipments() throws Exception {
        given().param("searchCriteria", "order_id=" + order_id)
                .param("fields", "items[tracks]").when()
                .get("/gfashion/v1/shipments").then().assertThat()
                .statusCode(200)
                .body("items.size()", greaterThanOrEqualTo(0));
    }
}
