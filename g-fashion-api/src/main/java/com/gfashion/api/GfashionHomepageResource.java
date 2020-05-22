package com.gfashion.api;

import com.gfashion.domain.homepage.CustomizedHomepage;
import com.gfashion.restclient.MangentoHomepageClient;
import com.gfashion.restclient.magento.exception.CustomerNotFoundException;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class GfashionHomepageResource {
    private MangentoHomepageClient magentoHomepageClient;

    @GetMapping("/homepage")
    public ResponseEntity<CustomizedHomepage> getDefaultCustomizedHomepage() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoHomepageClient.getDefaultCustomizedHomepage());
        } catch (CustomerUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    /*@GetMapping("/homepage/{customerId}")
    public ResponseEntity<CustomizedHomepage> getCustomizedHomepage(@PathVariable Integer customerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoHomepageClient.getCustomizedHomepage(customerId));
        } catch (CustomerUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }*/


}
