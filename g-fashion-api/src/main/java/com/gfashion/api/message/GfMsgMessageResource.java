package com.gfashion.api.message;

import com.gfashion.api.utility.ExceptionStringFactory;
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
import com.gfashion.message.constant.GfMessageConstants;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/message/v1", produces = {"application/json;charset=UTF-8"})
@Validated
@AllArgsConstructor
public class GfMsgMessageResource {

    private GfMsgMessageService _msgService;

    private ExceptionStringFactory exceptionStringFactory;

    // TODO: SSO integration to get the sender id.
    private final String sender = "sender1";
    private final String receiver = "receiver1";

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
            response.setAction(GfMessageConstants.ACTION_SEND);
            response.setStatus(GfMessageConstants.MESSAGE_SUCCESS);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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
            response.setAction(GfMessageConstants.ACTION_BROADCAST);
            response.setStatus(GfMessageConstants.MESSAGE_SUCCESS);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/receive")
    public ResponseEntity<List<GfMsgMessageEntity>> receiveMessage(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "lang") String language,
            @RequestParam(value = "secondsAgo", required = true) @Min(1) Long secondsAgo,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {

        try {
            language = (null == language || "".equals(language)) ? GfMessageConstants.MESSAGE_DEFAULT_LANGUAGE : language;
            List<GfMsgMessageEntity> messages = _msgService.getMessages(receiver, secondsAgo, language, limit);
            return ResponseEntity.status(HttpStatus.OK).body(messages);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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
            response.setAction(GfMessageConstants.ACTION_DELETE);
            response.setStatus(GfMessageConstants.MESSAGE_SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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
            response.setAction(GfMessageConstants.ACTION_MARK_READ);
            response.setStatus(GfMessageConstants.MESSAGE_SUCCESS);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
