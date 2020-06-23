package com.gfashion.designer;

import com.gfashion.domain.designer.GfDesignerSearchAttributeValueMappings;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfashionDesignerIT {
    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void searchDesignerByWrongFieldNameShouldReturnException() throws Exception {
        Map<String, String> attributeValue = new HashMap<>();
        attributeValue.put("name1", "Miles");
        GfDesignerSearchAttributeValueMappings body = GfDesignerSearchAttributeValueMappings.builder().equalAttributeValueMappings(attributeValue).build();

        Gson gson = new Gson();
        Response response = RestAssured.given().header("Content-Type", "application/json")
                .body(gson.toJson(body)).post("/gfashion/v1/dynamodb/designers/_search");
        response.then().assertThat().
                statusCode(500);
    }

    @Test
    public void searchDesignerByCorrectFieldNameShouldReturnOk() throws Exception {
        Map<String, String> attributeValue = new HashMap<>();
        attributeValue.put("name", "川普");
        GfDesignerSearchAttributeValueMappings body = GfDesignerSearchAttributeValueMappings.builder().equalAttributeValueMappings(attributeValue).build();

        Gson gson = new Gson();
        Response response = RestAssured.given().header("Content-Type", "application/json;charset=utf-8")
                .body(gson.toJson(body)).post("/gfashion/v1/dynamodb/designers/_search");
        response.then().assertThat().
                statusCode(200).body(containsString("美国"));
    }

    @Test
    public void searchDesignerByFieldNameForManShouldReturnOk1() throws Exception {
        Map<String, String> attributeValue = new HashMap<>();
        attributeValue.put("forMan", "1");
        GfDesignerSearchAttributeValueMappings body = GfDesignerSearchAttributeValueMappings.builder().equalAttributeValueMappings(attributeValue).build();

        Gson gson = new Gson();
        Response response = RestAssured.given().header("Content-Type", "application/json")
                .body(gson.toJson(body)).post("/gfashion/v1/dynamodb/designers/_search");
        response.then().assertThat().
                statusCode(200).body(containsString("Miles"));
    }

    @Test
    public void searchDesignerByFieldNameForWomanShouldReturnOk2() throws Exception {
        Map<String, String> attributeValue = new HashMap<>();
        attributeValue.put("forWoman", "1");
        GfDesignerSearchAttributeValueMappings body = GfDesignerSearchAttributeValueMappings.builder().equalAttributeValueMappings(attributeValue).build();

        Gson gson = new Gson();
        Response response = RestAssured.given().header("Content-Type", "application/json")
                .body(gson.toJson(body)).post("/gfashion/v1/dynamodb/designers/_search");
        response.then().assertThat().
                statusCode(200).body(containsString("斯诺"));
    }
}
