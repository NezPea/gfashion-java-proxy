package com.gfashion.api.dynamodb.service.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.gfashion.api.dynamodb.service.GfContactUsLogService;
import com.gfashion.data.GfContactUsLogEntity;
import com.gfashion.data.repository.dynamodb.GfContactUsLogRepositoryMethods;
import com.gfashion.data.repository.dynamodb.constant.Constant;
import com.gfashion.data.repository.dynamodb.key.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/11 15:08
 */
@Service
public class GfContactUsLogServiceImpl implements GfContactUsLogService {

    @Autowired
    private GfContactUsLogRepositoryMethods gfContactUsLogRepository;

    @Autowired
    private SequenceService sequenceService;

    @Override
    public GfContactUsLogEntity saveGfContactUsLog(GfContactUsLogEntity gfContactUsLogEntity) {
        final String gfContactUsLogSeq = sequenceService.getGfContactUsLogSeq(LocalDateTime.now());
        gfContactUsLogEntity.setSeq(gfContactUsLogSeq);

        gfContactUsLogEntity.setCreateTime(Constant.yyyyMMddHHmmssDf.format(LocalDateTime.now()));

        final GfContactUsLogEntity saved = gfContactUsLogRepository.saveGfContactUsLog(gfContactUsLogEntity);
        return saved;
    }

    @Override
    public QueryResultPage<GfContactUsLogEntity> getContactUsLog(String contactUsId, String seq, Integer limit, String lastId) {
        final QueryResultPage<GfContactUsLogEntity> contactUsLogs = gfContactUsLogRepository.getContactUsLog(contactUsId, seq, limit, lastId);
        return contactUsLogs;
    }


}
