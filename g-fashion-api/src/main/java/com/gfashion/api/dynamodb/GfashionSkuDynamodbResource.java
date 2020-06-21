package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.log.annotation.DDBLog;
import com.gfashion.data.repository.dynamodb.entity.GfSkuEntity;
import com.gfashion.data.repository.dynamodb.Interface.GfSkuRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionSkuDynamodbResource {

    private GfSkuRepository skuRepository;

    @DDBLog(operationType = "custom_operation_type", operationEvent = "custom_operation_event")
    @PostMapping(value = "/dynamodb/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfSkuEntity> createSku(@RequestBody GfSkuEntity sku) {
        try {
            GfSkuEntity response = skuRepository.createGfSkuEntity(sku);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfSkuEntity> getSku(@RequestParam(required = true) String productId,@RequestParam(required = true)  String skuId) {
        try {
            GfSkuEntity response = skuRepository.readGfSkuEntityById(productId,skuId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping(value = "/dynamodb/load/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<List<GfSkuEntity>> scanSku(@RequestBody List<GfSkuEntity> skuList) {
        try {
            List<GfSkuEntity> response = skuRepository.loadGfSkuEntity(skuList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/scan/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<List<GfSkuEntity>> scanSku(@RequestParam(required = true) String color) {
        try {
            List<GfSkuEntity> response = skuRepository.scanGfSkuEntityByColor(color);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }


    @PutMapping(value = "/dynamodb/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfSkuEntity> updateSku(@RequestBody GfSkuEntity sku) {
        try {
            if (skuRepository.readGfSkuEntityById(sku.getProductId(),sku.getSkuId()) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfSkuEntity response = skuRepository.updateGfSkuEntity(sku);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/dynamodb/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfSkuEntity> deleteSku(@RequestParam(required = true)  String productId,@RequestParam(required = true)  String skuId) {
        try {
            if (skuRepository.readGfSkuEntityById(productId,skuId) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            skuRepository.deleteGfSkuEntity(productId,skuId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
