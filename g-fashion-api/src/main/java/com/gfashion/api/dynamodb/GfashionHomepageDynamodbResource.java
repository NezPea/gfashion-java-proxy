package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.data.repository.dynamodb.GfHomepageRepository;
import com.gfashion.domain.homepage.CustomizedHomepage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionHomepageDynamodbResource {

    private GfHomepageRepository gfHomepageRepository;

    @GetMapping("/dynamodb/homepage")
    public ResponseEntity<CustomizedHomepage> getDefaultCustomizedHomepage() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gfHomepageRepository.getDefaultCustomizedHomepageReflection());
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
