package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.log.annotation.DDBLog;
import com.gfashion.data.GfCategoryEntity;
import com.gfashion.data.repository.dynamodb.GfCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionCategoryDynamodbResource {

    private GfCategoryRepository categoryRepository;

    @DDBLog(operationType = "custom_operation_type", operationEvent = "custom_operation_event")
    @PostMapping(value = "/dynamodb/categories", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfCategoryEntity> createCategory(@RequestBody GfCategoryEntity category) {
        try {
            GfCategoryEntity response = categoryRepository.createGfCategoryEntity(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/categories/{categoryId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfCategoryEntity> getCategory(@PathVariable String categoryId) {
        try {
            GfCategoryEntity response = categoryRepository.readGfCategoryEntityById(categoryId);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/dynamodb/categories", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfCategoryEntity> updateCategory(@RequestBody GfCategoryEntity category) {
        try {
            if (categoryRepository.readGfCategoryEntityById(category.getId()) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfCategoryEntity response = categoryRepository.createGfCategoryEntity(category);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/dynamodb/categories/{categoryId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfCategoryEntity> deleteCategory(@PathVariable String categoryId) {
        try {
            if (categoryRepository.readGfCategoryEntityById(categoryId) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            categoryRepository.deleteGfCategoryEntity(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
