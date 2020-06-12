package com.gfashion.api.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.gfashion.api.dynamodb.service.GfContactUsLogService;
import com.gfashion.data.GfContactUsLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author ：勉强生
 * @description：TODO
 * @date ：2020/6/12 13:25
 */
@RestController
@RequestMapping(path = "/gfashion/v1")
public class GfContactUsLogDynamodbResource {

    @Autowired
    private GfContactUsLogService gfContactUsLogService;

    @PutMapping(value = "/dynamodb/contactUsLog", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfContactUsLogEntity> insertGfContactUsLog(@RequestBody GfContactUsLogEntity contactUsLog) {

        final GfContactUsLogEntity gfContactUsLogEntity = gfContactUsLogService.saveGfContactUsLog(contactUsLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(gfContactUsLogEntity);
    }

    /**
     *
     * @param contactUsId 客服日志partition key，必传
     * @param seq 客服日志range key,如果传，则查询唯一一条日志记录
     * @param limit 分页查询指定返回数量
     * @param lastId 使用上一次查询返回的seq值传递，作为下一页的起始记录
     * @return
     */
    @GetMapping(value = "/dynamodb/contactUsLog/get", produces = "application/json;charset=utf-8")
    public ResponseEntity<QueryResultPage<GfContactUsLogEntity>> getContactUsLog(String contactUsId, String seq, Integer limit, String lastId){

        final QueryResultPage<GfContactUsLogEntity> contactUsLogs = gfContactUsLogService.getContactUsLog(contactUsId, seq, limit, lastId);
        return ResponseEntity.status(HttpStatus.OK).body(contactUsLogs);
    }
}
