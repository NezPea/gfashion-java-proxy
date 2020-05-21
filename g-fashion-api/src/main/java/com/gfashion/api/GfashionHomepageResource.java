package com.gfashion.api;

import com.gfashion.restclient.MangentoHomepageClient;
import com.gfashion.restclient.magento.exception.CustomerNotFoundException;
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
    public ResponseEntity getDefaultCustomizedHomepage() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoHomepageClient.getDefaultCustomizedHomepage());
        } catch (CustomerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found", e);
        }
    }

    @GetMapping("/homepage/{customerId}")
    public ResponseEntity getCustomizedHomepage(@PathVariable Integer customerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoHomepageClient.getCustomizedHomepage(customerId));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getErrorMessage());
        }
    }


}
