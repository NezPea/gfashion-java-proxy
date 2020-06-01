package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.data.GfDesignerEntity;
import com.gfashion.data.repository.dynamodb.GfDesignerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionDesignerDynamodbResource {

    private GfDesignerRepository designerRepository;

    @PostMapping("/dynamodb/designers")
    public ResponseEntity<GfDesignerEntity> createdesigner(@RequestBody GfDesignerEntity designer) {
        try {
            GfDesignerEntity response = designerRepository.createGfDesignerEntity(designer);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/dynamodb/designers/{designerId}")
    public ResponseEntity<GfDesignerEntity> getdesigner(@PathVariable String designerId) {
        try {
            GfDesignerEntity response = designerRepository.readGfDesignerEntity(designerId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/dynamodb/designers")
    public ResponseEntity<GfDesignerEntity> updatedesigner(@RequestBody GfDesignerEntity designer) {
        try {
            if (null == designer || null == designer.getId()) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfDesignerEntity response = designerRepository.updateGfDesignerEntity(designer);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/dynamodb/designers/{designerId}")
    public ResponseEntity<GfDesignerEntity> deletedesigner(@PathVariable String designerId) {
        try {
            if (null == designerRepository.readGfDesignerEntity(designerId)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            designerRepository.deleteGfDesignerEntity(designerId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
