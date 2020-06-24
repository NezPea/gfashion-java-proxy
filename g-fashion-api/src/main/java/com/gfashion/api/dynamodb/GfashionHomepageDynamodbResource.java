package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.api.utility.ExceptionStringFactory;
import com.gfashion.data.repository.dynamodb.interfaces.GfHomepageRepository;
import com.gfashion.domain.homepage.CustomizedHomepage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionHomepageDynamodbResource {

    private GfHomepageRepository gfHomepageRepository;

    private ExceptionStringFactory exceptionStringFactory;

    @GetMapping(value = "/dynamodb/homepage", produces = "application/json;charset=utf-8")
    public ResponseEntity<CustomizedHomepage> getDefaultCustomizedHomepage(@RequestParam(required = false) String locale) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gfHomepageRepository.getDefaultCustomizedHomepageBatchQuery(locale));
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()),
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.valueOf(e.getStatusCode())));
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
