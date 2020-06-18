package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.log.annotation.DDBLog;
import com.gfashion.data.GfSkuCopyEntity;
import com.gfashion.data.repository.dynamodb.GfSkuCopyRepository;
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
public class GfashionSkuCopyDynamodbResource {

    private GfSkuCopyRepository skuRepository;

    @DDBLog(operationType = "custom_operation_type", operationEvent = "custom_operation_event")
    @PostMapping(value = "/dynamodb/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfSkuCopyEntity> createSkuCopy(@RequestBody GfSkuCopyEntity sku) {
        try {
            GfSkuCopyEntity response = skuRepository.createGfSkuCopyEntity(sku);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfSkuCopyEntity> getSkuCopy(@RequestParam(required = true) String productId,@RequestParam(required = true)  String skuId) {
        try {
            GfSkuCopyEntity response = skuRepository.readGfSkuCopyEntityById(productId,skuId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/dynamodb/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfSkuCopyEntity> updateSkuCopy(@RequestBody GfSkuCopyEntity sku) {
        try {
            if (skuRepository.readGfSkuCopyEntityById(sku.getProductId(),sku.getSkuId()) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfSkuCopyEntity response = skuRepository.updateGfSkuCopyEntity(sku);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/dynamodb/skus", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfSkuCopyEntity> deleteSkuCopy(@RequestParam(required = true)  String productId,@RequestParam(required = true)  String skuId) {
        try {
            if (skuRepository.readGfSkuCopyEntityById(productId,skuId) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            skuRepository.deleteGfSkuCopyEntity(productId,skuId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
