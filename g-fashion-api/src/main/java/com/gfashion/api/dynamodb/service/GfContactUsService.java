package com.gfashion.api.dynamodb.service;

import com.gfashion.data.GfContactUsEntity;

/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/8 21:42
 */
public interface GfContactUsService {

    /**
     * 保存客服服务记录
     * @param gfContactUsEntity
     * @return
     */
    GfContactUsEntity saveGfContactUs(GfContactUsEntity gfContactUsEntity);

    /**
     * 根据主键获取客服服务记录
     * @param contactUsId
     * @return
     */
    GfContactUsEntity getContactUs(String contactUsId);

    /**
     * 更新客服服务记录
     * @param gfContactUsEntity
     * @return
     */
    GfContactUsEntity updateContactUs(GfContactUsEntity gfContactUsEntity);
}
