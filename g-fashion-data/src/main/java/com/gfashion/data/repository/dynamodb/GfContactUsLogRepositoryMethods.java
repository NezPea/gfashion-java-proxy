package com.gfashion.data.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.gfashion.data.GfContactUsLogEntity;


/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/12 8:38
 */
public interface GfContactUsLogRepositoryMethods {

    /**
     * 保存客服日志记录
     * @param gfContactUsLogEntity
     * @return
     */
    GfContactUsLogEntity saveGfContactUsLog(GfContactUsLogEntity gfContactUsLogEntity);

    /**
     * 获取客服日志记录
     * @param contactUsId
     * @param seq
     * @param limit
     * @param lastId
     * @return
     */
    QueryResultPage<GfContactUsLogEntity> getContactUsLog(String contactUsId, String seq, Integer limit, String lastId);
}
