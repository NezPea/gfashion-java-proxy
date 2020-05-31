package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.data.GfProductEntity;
import com.gfashion.data.repository.dynamodb.ProductRepository;
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
public class GfashionPorductDynamodbResource {

    private ProductRepository productRepository;

    @PostMapping("/dynamodb/products")
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

    @GetMapping("/dynamodb/products/{productId}")
    public ResponseEntity<GfProductEntity> getProduct(@PathVariable String productId) {
        try {
            GfProductEntity response = productRepository.readGfProductEntity(productId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/dynamodb/products")
    public ResponseEntity<GfProductEntity> updateProduct(@RequestBody GfProductEntity product) {
        try {
            GfProductEntity response = productRepository.updateGfProductEntity(product);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/dynamodb/products/{productId}")
    public ResponseEntity<GfProductEntity> deleteProduct(@PathVariable String productId) {
        try {
            productRepository.deleteGfProductEntity(productId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
