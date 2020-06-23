package com.gfashion.message.repository;

import com.gfashion.message.GfMsgBroadcastStatusEntity;

import java.util.List;

public interface GfMsgBroadcastStatusRepository {

    /**
     * Add new broadcast status message into database.
     * This method will also be used to update the message.
     * Including e.g. marking the message as read.
     * marking the message as deleted etc.
     * @param msg the message entity to save.
     */
    void add(GfMsgBroadcastStatusEntity msg);

    /**
     * Find a message by its message id.
     * @param receiver The message receiver.
     * @param msgId The message id.
     * @return A message of class broadcast status.
     */
    GfMsgBroadcastStatusEntity findById(String receiver, String msgId);

    /**
     * Find all message for receiver.
     * @param receiver The recipient of the message.
     * @param secondsAgo How long (in seconds) ago should we search till.
     * @param countLimit If there is too many message, use this is limit how many will be returned.
     * @return
     */
    List<GfMsgBroadcastStatusEntity> findAll(String receiver, Long secondsAgo, Integer countLimit);

}
