package com.gfashion.api.message.impl;

import com.amazonaws.AmazonServiceException;
import com.gfashion.api.message.GfMsgMessageService;
import com.gfashion.api.message.MessageRequest;
import com.gfashion.api.message.MessageType;
import com.gfashion.message.GfMsgBroadcastStatusEntity;
import com.gfashion.message.GfMsgMessageEntity;
import com.gfashion.message.repository.GfMsgBroadcastStatusRepository;
import com.gfashion.message.repository.GfMsgMessageRepository;
import com.gfashion.message.constant.GfMessageConstants;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GfMsgMessageServiceImpl implements GfMsgMessageService {

    @Autowired
    GfMsgMessageRepository _msgRepository;

    @Autowired
    GfMsgBroadcastStatusRepository _broadcastMsgStatusRepository;

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

        message.setTtl(ts / 1000 + GfMessageConstants.TTL_PERSONAL_BROADCAST);
        message.setId(msgId);
        message.setSender(sender);
        message.setTitle(msg.getTitle());
        message.setContent(msg.getContent());
        message.setPicture(msg.getPicture());
        message.setTimeSent(ts);
        message.setCategory(GfMessageConstants.MESSAGE_DEFAULT_CATEGORY);
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
    public List<GfMsgMessageEntity> getMessages(String receiver, Long secondsAgo, String language, Integer countLimit) {
        List<GfMsgMessageEntity> messages = _msgRepository.findAll(receiver, secondsAgo, countLimit);
        List<GfMsgMessageEntity> updatedMessages = messages.stream().map(message -> {

            String k, v;
            if (message.getTitle().containsKey(language)) {
                k = language;
                v = message.getTitle().get(k);
                message.getTitle().clear();
                message.getTitle().put(k, v);
            } else if (message.getTitle().containsKey(GfMessageConstants.MESSAGE_DEFAULT_LANGUAGE)) {
                v = message.getTitle().get(GfMessageConstants.MESSAGE_DEFAULT_LANGUAGE);
                message.getTitle().clear();
                message.getTitle().put(GfMessageConstants.MESSAGE_DEFAULT_LANGUAGE, v);
            } else {
                throw new AmazonServiceException("The title should at least contains value for language en.");
            }

            if (message.getContent().containsKey(language)) {
                k = language;
                v = message.getContent().get(k);
                message.getContent().clear();
                message.getContent().put(k, v);
            } else if (message.getContent().containsKey(GfMessageConstants.MESSAGE_DEFAULT_LANGUAGE)) {
                v = message.getContent().get(GfMessageConstants.MESSAGE_DEFAULT_LANGUAGE);
                message.getContent().clear();
                message.getContent().put(GfMessageConstants.MESSAGE_DEFAULT_LANGUAGE, v);
            } else {
                throw new AmazonServiceException("The content should at least contains value for language en.");
            }

            return message;
        }).collect(Collectors.toList());

        // filter out messages that has been marked as deleted.
        // TODO:

        // update the status of the broadcast messages.
        List<GfMsgBroadcastStatusEntity> msgStatuses = _broadcastMsgStatusRepository.findAll(receiver, secondsAgo, countLimit);
        if (null != msgStatuses || 0 < msgStatuses.size()) {

            updatedMessages.forEach(message -> {
                GfMsgBroadcastStatusEntity msgStatus;
                if (GfMessageConstants.BROADCAST_RECEIVER == message.getReceiver()) {
                    msgStatus = IterableUtils.find(msgStatuses,
                        new Predicate<GfMsgBroadcastStatusEntity>() {
                            @Override
                            public boolean evaluate(GfMsgBroadcastStatusEntity status) {
                                return status.getId() == message.getId();
                            }
                        });
                    if (null != msgStatus) {
                        if (msgStatus.getDeleted()) {
                            updatedMessages.remove(message);
                        } else {
                            message.setOpened(msgStatus.getOpened());
                            message.setTimeOpened(msgStatus.getTimeOpened());
                            message.setTimeUpdated(msgStatus.getTimeUpdated());
                        }
                    }
                }
            });
        }

        return updatedMessages;
    }

    @Override
    public GfMsgMessageEntity getMessageById(String receiver, String msgId) {
        return _msgRepository.findById(receiver, msgId);
    }

    @Override
    public void markRead(String receiver, String msgId) {
        GfMsgMessageEntity msg = _msgRepository.findById(receiver, msgId);

        final Instant now = Instant.now();
        final Long ts = now.toEpochMilli();
        if (null != msg) {

            msg.setOpened(true);
            msg.setTimeUpdated(ts);
            msg.setTimeOpened(ts);

            _msgRepository.add(msg);
        } else {
            // check if this is a broadcast message.
            msg = _msgRepository.findById(GfMessageConstants.BROADCAST_RECEIVER, msgId);
            if (null == msg) {
                throw new AmazonServiceException("Message " + msgId + " doesn't exist.");
            }
            GfMsgBroadcastStatusEntity msgStatus = _broadcastMsgStatusRepository.findById(receiver, msgId);

            if (null == msgStatus) {
                msgStatus = new GfMsgBroadcastStatusEntity();
                msgStatus.setId(msg.getId());
                msgStatus.setReceiver(receiver);
                msgStatus.setTtl(ts / 1000 + GfMessageConstants.TTL_PERSONAL_BROADCAST);
            }

            msgStatus.setOpened(true);
            msgStatus.setTimeSent(msg.getTimeSent());
            msgStatus.setTimeUpdated(ts);
            msgStatus.setTimeOpened(ts);

            _broadcastMsgStatusRepository.add(msgStatus);
        }
    }

    @Override
    public void deleteMessage(String receiver, String msgId) {
        GfMsgMessageEntity msg = _msgRepository.findById(receiver, msgId);
        if (null != msg) {
            _msgRepository.receiverDelete(receiver, msgId);
        } else {
            msg = _msgRepository.findById(GfMessageConstants.BROADCAST_RECEIVER, msgId);
            if (null == msg) {
                throw new AmazonServiceException("Message " + msgId + " doesn't exist.");
            }

            // ok, this is a broadcast message, mark it as delete in the status table.
            GfMsgBroadcastStatusEntity msgStatus = new GfMsgBroadcastStatusEntity();
            final Instant now = Instant.now();
            final Long ts = now.toEpochMilli();
            msgStatus.setTtl(ts / 1000 + GfMessageConstants.TTL_PERSONAL_BROADCAST);
            msgStatus.setId(msgId);
            msgStatus.setReceiver(receiver);
            msgStatus.setTimeSent(msg.getTimeSent());
            msgStatus.setDeleted(true);

            _broadcastMsgStatusRepository.add(msgStatus);
        }
    }

    @Override
    public void unSend(String sender, String msgId) {
        _msgRepository.senderDelete(sender, msgId);
    }
}
