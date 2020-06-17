package com.gfashion.data.service;


import com.gfashion.data.GfContactUsEntity;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface GfContactUsService {

    void updateContactStatus(String contactId, String serviceId, Integer status);

    Map<String, String> getContactStatus();

    Map<String, String> getContactType();

    Map<String, Object> siteMailToContact(Map<String, Object> jsonData);

    Map<String, Object> customFirstToService(Map<String, Object> jsonData);

    Map<String, Object> cycleToCustom(Map<String, Object> jsonData);

    Map<String, Object> cycleToService(Map<String, Object> jsonData);

    Map<String, Object> customSendMessage(Map<String, Object> jsonData);

    Map<String, Object> serviceSendMessage(Map<String, Object> jsonData);

    Map<String, Object> getUnread(String customId);

    List<GfContactUsEntity> getContactList(Map<String,Object> map);

    void updateReadStatus(String contactUsId);

}
