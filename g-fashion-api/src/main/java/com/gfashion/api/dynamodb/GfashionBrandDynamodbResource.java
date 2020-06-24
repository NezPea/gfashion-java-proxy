package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.utility.ExceptionStringFactory;
import com.gfashion.data.repository.dynamodb.entity.GfBrandEntity;
import com.gfashion.data.repository.dynamodb.interfaces.GfBrandRepository;
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

    private ExceptionStringFactory exceptionStringFactory;

    @PostMapping(value = "/dynamodb/brands", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfBrandEntity> createBrand(@RequestBody GfBrandEntity brand) {
        try {
            GfBrandEntity response = brandRepository.createGfBrandEntity(brand);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()),
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.valueOf(e.getStatusCode())));
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping(value = "/dynamodb/brands/{brandId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfBrandEntity> getBrand(@PathVariable String brandId) {
        try {
            GfBrandEntity response = brandRepository.readGfBrandEntity(brandId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()),
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.valueOf(e.getStatusCode())));
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping(value = "/dynamodb/brands", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfBrandEntity> updateDesigner(@RequestBody GfBrandEntity brand) {
        try {
            if (null == brand || null == brandRepository.readGfBrandEntity(brand.getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfBrandEntity response = brandRepository.updateGfBrandEntity(brand);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()),
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.valueOf(e.getStatusCode())));
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping(value = "/dynamodb/brands/{brandId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfBrandEntity> deleteBrand(@PathVariable String brandId) {
        try {
            if (null == brandRepository.readGfBrandEntity(brandId)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            brandRepository.deleteGfBrandEntity(brandId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()),
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.valueOf(e.getStatusCode())));
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
