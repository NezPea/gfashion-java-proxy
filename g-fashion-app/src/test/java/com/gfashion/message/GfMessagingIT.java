package com.gfashion.message;

import com.gfashion.api.message.*;
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

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;

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

    @Autowired
    GfMsgMessageService _msgService;

    @Before
    public void setup() {
        RestAssured.port = port;

        HashMap<String, String> msgTitle = new HashMap<>();
        msgTitle.put("zh_CN", "中文标题");
        msgTitle.put("en", "English title");

        HashMap<String, String> msgContent = new HashMap<>();
        msgContent.put("zh_CN", "这是中文内容");
        msgContent.put("en", "English content.");

        testMessage = new MessageRequest();
        testMessage.setTitle(msgTitle);
        testMessage.setContent(msgContent);
        testMessage.setReceiver(receiver);
    }

    @Test
    public void testMsgServiceConversationAPI() throws Exception {
        // test the service layer API.
        // send.
        String msgId = _msgService.saveMessage(sender, testMessage, MessageType.CONVERSATION);
        assertFalse(null == msgId);

        // receive.
        List<GfMsgMessageEntity> msgs = _msgService.getMessages(receiver, (long) 1000, "zh_CN", 10);
        assertFalse(null == msgs);
        assertFalse(msgs.size() == 0);
        assertFalse(msgs.get(0).getOpened() == true);

        // mark read.
        _msgService.markRead(receiver, msgId);

        // delete message.
        _msgService.deleteMessage(receiver, msgId);

        // try receive again.
        msgs = _msgService.getMessages(receiver, (long) 1, "zh_CN", 10);
        assertFalse(null == msgs);
    }

    @Test
    public void testMsgServiceBroadcastAPI() throws Exception {
        // send.
        String msgId = _msgService.saveMessage(sender, testMessage, MessageType.BROADCAST);
        assertFalse(null == msgId);

        // receive.
        List<GfMsgMessageEntity> msgs = _msgService.getMessages(receiver, (long) 1000, "zh_CN", 10);
        assertFalse(null == msgs);
        assertFalse(msgs.size() == 0);
        assertFalse(msgs.get(0).getOpened() == true);

        // mark read.
        _msgService.markRead(receiver, msgId);

        // delete message.
        _msgService.deleteMessage(receiver, msgId);

        // try receive again.
        msgs = _msgService.getMessages(receiver, (long) 1, "zh_CN", 10);
        assertFalse(msgs.size() > 0);
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
                .header("lang", "zh_CN")
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
