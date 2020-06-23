package com.gfashion.api.message;

import com.gfashion.message.GfMsgMessageEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GfMsgMessageService {
    /**
     * Save message to database.
     * @param sender The sender of the message.
     * @param msg The MessageRequest object that contains the required fields.
     * @param type Type of the message, BROADCAST or CONVERSATION.
     * @return The message id created in the database.
     */
    String saveMessage(String sender, MessageRequest msg, MessageType type);

    /**
     * Retrieve a user's message starting from [num] seconds ago.
     * @param receiver The receiver of the message.
     * @param secondsAgo Retrieve message from [num] seconds ago.
     * @param language Which language of content to return.
     * @param countLimit Only retrieve a limited number of messages.
     * @return A list of message entities.
     */
    List<GfMsgMessageEntity> getMessages(String receiver, Long secondsAgo, String language, Integer countLimit);

    /**
     * Retrieve a message with id.
     * @param receiver The receiver of the message.
     * @param msgId The message id.
     * @return The message or null.
     */
    GfMsgMessageEntity getMessageById(String receiver, String msgId);

    /**
     * Mark a message send to receiver as read.
     * @param receiver The receiver of the message.
     * @param msgId The message id of the message.
     * @return void
     */
    void markRead(String receiver, String msgId);

    /**
     * A receiver delete a message with given id.
     * @param msgId The message id.
     * @return void
     */
    void deleteMessage(String receiver, String msgId);

    /**
     * A sender delete a message that was sent.
     * @param sender The sender who sends the message.
     * @param msgId The message id that was sent.
     * @return void
     */
    void unSend(String sender, String msgId);
}
