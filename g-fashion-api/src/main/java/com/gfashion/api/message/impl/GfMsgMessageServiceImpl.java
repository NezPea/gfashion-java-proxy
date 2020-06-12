package com.gfashion.api.message.impl;

import com.amazonaws.AmazonServiceException;
import com.gfashion.api.message.GfMsgMessageService;
import com.gfashion.api.message.MessageRequest;
import com.gfashion.api.message.MessageType;
import com.gfashion.message.GfMsgMessageEntity;
import com.gfashion.message.repository.GfMsgMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class GfMsgMessageServiceImpl implements GfMsgMessageService {

    @Autowired
    GfMsgMessageRepository _msgRepository;

    @Override
    public String saveMessage(String sender, MessageRequest msg, MessageType type) {

        final Instant now = Instant.now(); // UTC time in milliseconds.
        final Long ts = now.toEpochMilli();

        // generate message id from string of (receiver + sendTime).
        final String uuidSeed = msg.getReceiver() + '-' + String.valueOf(ts);
        final String msgId = UUID.nameUUIDFromBytes(uuidSeed.getBytes()).toString();

        GfMsgMessageEntity message = new GfMsgMessageEntity();
        if (MessageType.CONVERSATION == type) {

            message.setReceiver(msg.getReceiver());
            // TODO: make sure the receiver exists, otherwise throw ReceiverNotExistError.
            // TODO: from security perspective, limit how many messages a user can send within a time range.
            // TODO: check that the receiver has blocked the user from sending messages.

        } else {

            message.setReceiver(type.name());
            // TODO: make sure that the user has permission to do broadcast.

        }

        message.setId(msgId);
        message.setSender(sender);
        message.setTitle(msg.getTitle());
        message.setContent(msg.getContent());
        message.setPicture(msg.getPicture());
        message.setTimeSent(ts);
        message.setCategory("normal");
        message.setTimeUpdated(ts);
        message.setUpdatedBy(sender);
        message.setOpened(false);
        message.setTimeOpened((long) 0);
        message.setContract(msg.getContract());
        message.setData(msg.getData());

        _msgRepository.add(message);
        return msgId;
    }

    @Override
    public List<GfMsgMessageEntity> getMessages(String receiver, Long secondsAgo, Integer countLimit) {
        return _msgRepository.findAll(receiver, secondsAgo, countLimit);
    }

    @Override
    public GfMsgMessageEntity getMessageById(String receiver, String msgId) {
        return _msgRepository.findById(receiver, msgId);
    }

    @Override
    public void markRead(String receiver, String msgId) {
        GfMsgMessageEntity msg = _msgRepository.findById(receiver, msgId);

        if (null == msg) {
            throw new AmazonServiceException("Message " + msgId + " doesn't exist.");
        }

        final Instant now = Instant.now();
        final Long ts = now.toEpochMilli();
        msg.setOpened(true);
        msg.setTimeUpdated(ts);
        msg.setTimeOpened(ts);

        _msgRepository.add(msg);
    }

    @Override
    public void deleteMessage(String receiver, String msgId) {
        _msgRepository.receiverDelete(receiver, msgId);
    }

    @Override
    public void unSend(String sender, String msgId) {
        _msgRepository.senderDelete(sender, msgId);
    }
}
