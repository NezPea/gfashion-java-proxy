package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.data.GfVendorEntity;
import com.gfashion.data.repository.dynamodb.VendorRepository;
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

    @PostMapping("/dynamodb/vendors")
    public ResponseEntity<GfVendorEntity> createVendor(@RequestBody GfVendorEntity vendor) {
        try {
            GfVendorEntity response = vendorRepository.createGfVendorEntity(vendor);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/dynamodb/vendors/{vendorId}")
    public ResponseEntity<GfVendorEntity> getVendor(@PathVariable String vendorId) {
        try {
            GfVendorEntity response = vendorRepository.readGfVendorEntity(vendorId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/dynamodb/vendors")
    public ResponseEntity<GfVendorEntity> updateVendor(@RequestBody GfVendorEntity vendor) {
        try {
            if (null == vendor || null == vendorRepository.readGfVendorEntity(vendor.getVendorId())) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfVendorEntity response = vendorRepository.updateGfVendorEntity(vendor);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/dynamodb/vendors/{vendorId}")
    public ResponseEntity<GfVendorEntity> deleteVendor(@PathVariable String vendorId) {
        try {
            if (null == vendorRepository.readGfVendorEntity(vendorId)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            vendorRepository.deleteGfVendorEntity(vendorId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
