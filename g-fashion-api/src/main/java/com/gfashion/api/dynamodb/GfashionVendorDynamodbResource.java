package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.utility.ExceptionStringFactory;
import com.gfashion.data.repository.dynamodb.entity.GfVendorEntity;
import com.gfashion.data.repository.dynamodb.interfaces.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionVendorDynamodbResource {

    private VendorRepository vendorRepository;

    private ExceptionStringFactory exceptionStringFactory;

    @PostMapping(value = "/dynamodb/vendors", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfVendorEntity> createVendor(@RequestBody GfVendorEntity vendor) {
        try {
            GfVendorEntity response = vendorRepository.createGfVendorEntity(vendor);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()),
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.valueOf(e.getStatusCode())));
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping(value = "/dynamodb/vendors/{vendorId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfVendorEntity> getVendor(@PathVariable String vendorId) {
        try {
            GfVendorEntity response = vendorRepository.readGfVendorEntity(vendorId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()),
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.valueOf(e.getStatusCode())));
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping(value = "/dynamodb/vendors", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfVendorEntity> updateVendor(@RequestBody GfVendorEntity vendor) {
        try {
            if (null == vendor || null == vendorRepository.readGfVendorEntity(vendor.getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfVendorEntity response = vendorRepository.updateGfVendorEntity(vendor);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()),
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.valueOf(e.getStatusCode())));
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping(value = "/dynamodb/vendors/{vendorId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfVendorEntity> deleteVendor(@PathVariable String vendorId) {
        try {
            if (null == vendorRepository.readGfVendorEntity(vendorId)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            vendorRepository.deleteGfVendorEntity(vendorId);
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
