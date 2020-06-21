package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.log.annotation.DDBLog;
import com.gfashion.data.repository.dynamodb.entity.GfProductRecEntity;
import com.gfashion.data.repository.dynamodb.Interface.GfProductRecRepository;
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
public class GfashionProductRecDynamodbResource {

    private GfProductRecRepository productRecRepository;

    @DDBLog(operationType = "custom_operation_type", operationEvent = "custom_operation_event")
    @PostMapping(value = "/dynamodb/productRecs", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductRecEntity> createProductRec(@RequestBody GfProductRecEntity productRec) {
        try {
            GfProductRecEntity response = productRecRepository.createGfProductRecEntity(productRec);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/productRecs/{productRecId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductRecEntity> getProductRec(@PathVariable String productRecId) {
        try {
            GfProductRecEntity response = productRecRepository.readGfProductRecEntityById(productRecId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/dynamodb/productRecs", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductRecEntity> updateProductRec(@RequestBody GfProductRecEntity productRec) {
        try {
            if (productRecRepository.readGfProductRecEntityById(productRec.getId()) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfProductRecEntity response = productRecRepository.updateGfProductRecEntity(productRec);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/dynamodb/productRecs/{productRecId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductRecEntity> deleteProductRec(@PathVariable String productRecId) {
        try {
            if (productRecRepository.readGfProductRecEntityById(productRecId) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            productRecRepository.deleteGfProductRecEntity(productRecId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
