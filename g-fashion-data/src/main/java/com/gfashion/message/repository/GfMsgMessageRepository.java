package com.gfashion.message.repository;

import com.gfashion.message.GfMsgMessageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GfMsgMessageRepository {

    /**
     * Add new message to database.
     * @param msg The message to save.
     */
    void add(GfMsgMessageEntity msg);

    /**
     * Get message by message id.
     * @param receiver send the message to who.
     * @param msgId the message to get.
     * @return
     */
    GfMsgMessageEntity findById(String receiver, String msgId);

    /**
     * Find all message for receiver.
     * @param receiver The recipient of the message.
     * @param secondsAgo How long (in seconds) ago should we search till.
     * @param countLimit If there is too many message, use this is limit how many will be returned.
     * @return
     */
    List<GfMsgMessageEntity> findAll(String receiver, Long secondsAgo, Integer countLimit);

    /**
     * Delete a message by the receiver.
     * @param receiver The receiver for the message.
     * @param msgId The message id to delete.
     */
    void receiverDelete(String receiver, String msgId);

    /**
     * Delete a message by the sender.
     * @param sender
     * @param msgId
     */
    void senderDelete(String sender, String msgId);

}
