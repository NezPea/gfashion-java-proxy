package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.data.GfBrandEntity;
import com.gfashion.data.repository.dynamodb.GfBrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionBrandDynamodbResource {

    private GfBrandRepository brandRepository;

    @PostMapping("/dynamodb/brands")
    public ResponseEntity<GfBrandEntity> createbrand(@RequestBody GfBrandEntity brand) {
        try {
            GfBrandEntity response = brandRepository.createGfBrandEntity(brand);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/dynamodb/brands/{brandId}")
    public ResponseEntity<GfBrandEntity> getbrand(@PathVariable String brandId) {
        try {
            GfBrandEntity response = brandRepository.readGfBrandEntity(brandId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/dynamodb/brands")
    public ResponseEntity<GfBrandEntity> updatedesigner(@RequestBody GfBrandEntity brand) {
        try {
            if (null == brand || null == brandRepository.readGfBrandEntity(brand.getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfBrandEntity response = brandRepository.updateGfBrandEntity(brand);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/dynamodb/brands/{brandId}")
    public ResponseEntity<GfBrandEntity> deletebrand(@PathVariable String brandId) {
        try {
            if (null == brandRepository.readGfBrandEntity(brandId)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            brandRepository.deleteGfBrandEntity(brandId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
