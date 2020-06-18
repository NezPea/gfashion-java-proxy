package com.gfashion.api.mysql;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.log.annotation.DDBLog;
import com.gfashion.data.repository.mysql.GfProductMySqlEntity;
import com.gfashion.data.repository.mysql.GfProductRepository;
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
public class GfashionProductMysqlResource {

    private GfProductRepository gfProductRepository;

    @DDBLog(operationType = "custom_operation_type", operationEvent = "custom_operation_event")
    @PostMapping(value = "/mysql/products", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductMySqlEntity> createProduct(@RequestBody GfProductMySqlEntity product) {

        GfProductMySqlEntity response = gfProductRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/mysql/products/{productId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductMySqlEntity> getProduct(@PathVariable String productId) {

        GfProductMySqlEntity response = gfProductRepository.findById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/mysql/products", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductMySqlEntity> updateProduct(@RequestBody GfProductMySqlEntity product) {

        if (gfProductRepository.findById(product.getId()) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        GfProductMySqlEntity response = gfProductRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping(value = "/mysql/products/{productId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductMySqlEntity> deleteProduct(@PathVariable String productId) {

        if (gfProductRepository.findById(productId) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        gfProductRepository.deleteById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }
}
