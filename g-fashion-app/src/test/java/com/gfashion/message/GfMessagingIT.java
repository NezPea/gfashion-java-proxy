package com.gfashion.message;

import com.gfashion.api.message.MessageIdRequest;
import com.gfashion.api.message.MessageRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.gson.Gson;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GfMessagingIT {
    @LocalServerPort
    private int port;

    @Autowired
    private Gson gson;

    private final String sender = "sender1";
    private final String receiver = "receiver1";

    private MessageRequest testMessage;

    @Before
    public void setup() {
        RestAssured.port = port;
        testMessage = new MessageRequest();
        testMessage.setTitle("This is a test message.");
        testMessage.setContent("This is some test content.");
        testMessage.setReceiver(receiver);
    }

    @Test
    public void testSend() throws Exception {

        given().header("Content-Type", ContentType.JSON)
                .header("Authorization", "sso_token")
                .body(gson.toJson(testMessage))
                .put("/message/v1/send")
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testBroadcast() throws Exception {
        given().header("Content-Type", ContentType.JSON)
                .header("Authorization", "sso_token")
                .body(gson.toJson(testMessage))
                .put("/message/v1/broadcast")
                .then().assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testReceive() throws Exception {
        given().header("Content-Type", ContentType.JSON)
                .header("Authorization", "sso_token")
                .param("secondsAgo", 1000)
                .param("limit", 10)
                .get("/message/v1/receive")
                .then().assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testMarkRead() throws Exception {
        MessageIdRequest id = new MessageIdRequest();
        id.setId("");

        given().header("Content-Type", ContentType.JSON)
                .header("Authorization", "sso_token")
                .param("id", "")
                .post("/message/v1/markRead")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testDelete() throws Exception {
        given().header("Content-Type", ContentType.JSON)
                .header("Authorization", "sso_token")
                .param("id", "")
                .delete("/message/v1/delete")
                .then().assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
