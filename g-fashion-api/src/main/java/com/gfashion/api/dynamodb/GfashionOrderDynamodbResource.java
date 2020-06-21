package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.data.repository.dynamodb.entity.GfOrderEntity;
import com.gfashion.data.repository.dynamodb.interfaces.GfOrderRepository;
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
public class GfashionOrderDynamodbResource {

    private GfOrderRepository gfOrderRepository;

    @PostMapping(value = "/dynamodb/orders", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfOrderEntity> createProduct(@RequestBody GfOrderEntity order) {
        try {
            GfOrderEntity response = gfOrderRepository.addGfOrderEntity(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/orders/{orderId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfOrderEntity> getProduct(@PathVariable String orderId) {
        try {
            GfOrderEntity response = gfOrderRepository.readGfOrderEntityById(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/dynamodb/orders", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfOrderEntity> updateProduct(@RequestBody GfOrderEntity order) {
        try {
            if (gfOrderRepository.readGfOrderEntityById(order.getId()) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfOrderEntity response = gfOrderRepository.updateGfOrderEntity(order);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/dynamodb/orders/{orderId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfOrderEntity> deleteProduct(@PathVariable String orderId) {
        try {
            if (gfOrderRepository.readGfOrderEntityById(orderId) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            gfOrderRepository.deleteGfOrderEntity(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
