package com.gfashion.api.message;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.message.GfMsgMessageEntity;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/message/v1", produces = {"application/json;charset=UTF-8"})
@Validated
@AllArgsConstructor
public class GfMsgMessageResource {

    @Autowired
    private GfMsgMessageService _msgService;

    // TODO: SSO integration to get the sender id.
    final private String sender = "sender1";
    final private String receiver = "receiver1";

    // TODO: sender can't send message to himself.
    // TODO: sender can't send message to empty receiver or unknown receiver.

    @PutMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(
            @RequestHeader(name = "Authorization") String token,
            @Valid @RequestBody MessageRequest msg) {
        try {
            MessageResponse response = new MessageResponse();
            String msgId = _msgService.saveMessage(sender, msg, MessageType.CONVERSATION);
            response.setId(msgId);
            response.setAction("SEND");
            response.setStatus("SUCCESS");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/broadcast")
    public ResponseEntity<MessageResponse> broadcastMessage(
            @RequestHeader(name = "Authorization") String token,
            @Valid @RequestBody MessageRequest msg) {
        try {
            MessageResponse response = new MessageResponse();
            String msgId = _msgService.saveMessage(sender, msg, MessageType.BROADCAST);
            response.setId(msgId);
            response.setAction("BROADCAST");
            response.setStatus("SUCCESS");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/receive")
    public ResponseEntity<List<GfMsgMessageEntity>> receiveMessage(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "lang") String language,
            @RequestParam(value = "secondsAgo", required = true) @Min(1) Long secondsAgo,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {

        try {
            language = (null == language || "".equals(language)) ? "en" : language;
            List<GfMsgMessageEntity> messages = _msgService.getMessages(receiver, secondsAgo, language, limit);
            return ResponseEntity.status(HttpStatus.OK).body(messages);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponse> deleteMessage(
            @RequestHeader(name = "Authorization") String token,
            @Valid @RequestBody MessageIdRequest msg) {
        try {
            _msgService.deleteMessage(receiver, msg.getId());
            MessageResponse response = new MessageResponse();
            response.setId(msg.getId());
            response.setAction("DELETE");
            response.setStatus("SUCCESS");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/markRead")
    public ResponseEntity<MessageResponse> markRead(
            @RequestHeader(name = "Authorization") String token,
            @Valid @RequestBody MessageIdRequest msg) {
        try {
            _msgService.markRead(receiver, msg.getId());
            MessageResponse response = new MessageResponse();
            response.setId(msg.getId());
            response.setAction("MARK_READ");
            response.setStatus("SUCCESS");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
