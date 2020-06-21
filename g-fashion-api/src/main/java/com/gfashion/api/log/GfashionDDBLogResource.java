package com.gfashion.api.log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.log.annotation.DDBLog;
import com.gfashion.data.repository.dynamodb.entity.LogEntity;
import com.gfashion.data.repository.dynamodb.Interface.DDBLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionDDBLogResource {

    private DDBLogRepository ddbLogRepository;

    @DDBLog(operationType = "DDB_Post", operationEvent = "POST_EVENT")
//    @PostMapping(value = "/dynamodb/log", produces = "application/json;charset=utf-8")
    public ResponseEntity<LogEntity> createLog(@RequestBody LogEntity log) {
        try {
            LogEntity response = ddbLogRepository.createGfLogEntity(log);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/log/{logId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<LogEntity> getLog(@PathVariable String logId) {
        try {
            LogEntity response = ddbLogRepository.readGfLogEntityById(logId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/dynamodb/log/{logId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<LogEntity> deleteLog(@PathVariable String logId) {
        try {
            if (ddbLogRepository.readGfLogEntityById(logId) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            ddbLogRepository.deleteGfLogEntity(logId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
