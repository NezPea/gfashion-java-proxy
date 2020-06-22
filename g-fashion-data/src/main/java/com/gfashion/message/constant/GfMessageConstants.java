package com.gfashion.message.constant;

public class GfMessageConstants {

    public static final String MESSAGE_DEFAULT_LANGUAGE = "en";

    public static final String MESSAGE_DEFAULT_CATEGORY = "normal";

    public static final String MESSAGE_SUCCESS = "SUCCESS";

    // API actions.

    public static final String ACTION_SEND = "SEND";

    public static final String ACTION_BROADCAST = "BROADCAST";

    public static final String ACTION_RECEIVE = "RECEIVE";

    public static final String ACTION_MARK_READ = "MARK_READ";

    public static final String ACTION_DELETE = "DELETE";

    public static final String DYNAMODB_LSI_NAME = "ReceiverMsgIdIndex";

    public static final String BROADCAST_RECEIVER = "BROADCAST";

    // Expiration time of message.

    public static final Long TTL_SYSTEM_BROADCASST = (long)60 * 60 * 24 * 30; // one month.

    public static final Long TTL_PERSONAL_BROADCAST = (long)60 * 60 * 24 * 30 * 6; // half a year.

    public static final Long TTL_CUSTOMER_SERVICE = null;

    // Error messages.
    public static final String MESSAGE_NOT_EXIST_ERROR = "Message does not exist";
}
