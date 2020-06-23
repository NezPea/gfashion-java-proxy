package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.log.annotation.DDBLog;
import com.gfashion.data.GfProductEntity;
import com.gfashion.data.repository.dynamodb.GfProductRepository;
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
public class GfashionProductDynamodbResource {

    private GfProductRepository productRepository;

    @DDBLog(operationType = "custom_operation_type", operationEvent = "custom_operation_event")
    @PostMapping(value = "/dynamodb/products", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductEntity> createProduct(@RequestBody GfProductEntity product) {
        try {
            GfProductEntity response = productRepository.createGfProductEntity(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/products/{productId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductEntity> getProduct(@PathVariable String productId) {
        try {
            GfProductEntity response = productRepository.readGfProductEntityById(productId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/dynamodb/products", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductEntity> updateProduct(@RequestBody GfProductEntity product) {
        try {
            if (productRepository.readGfProductEntityById(product.getId()) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfProductEntity response = productRepository.updateGfProductEntity(product);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/dynamodb/products/{productId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductEntity> deleteProduct(@PathVariable String productId) {
        try {
            if (productRepository.readGfProductEntityById(productId) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            productRepository.deleteGfProductEntity(productId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
