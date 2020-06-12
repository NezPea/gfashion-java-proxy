package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfContactUsEntity;

/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/12 8:37
 */
public interface GfContactUsRepositoryMethods {

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
    GfContactUsEntity getContactUsById(String contactUsId);


}
