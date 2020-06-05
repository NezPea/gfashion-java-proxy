package com.gfashion.api;

import com.gfashion.domain.homepage.CustomizedHomepage;
import com.gfashion.domain.homepage.GfCategory;
import com.gfashion.restclient.MagentoHomepageClient;
import com.gfashion.restclient.MagentoMockDataClient;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class GfashionHomepageResource {

    private MagentoMockDataClient magentoMockDataClient;

    private MagentoHomepageClient magentoHomepageClient;

    @GetMapping(value = "/homepage", produces = "application/json;charset=utf-8")
    public ResponseEntity<CustomizedHomepage> getDefaultCustomizedHomepage() {
        return ResponseEntity.status(HttpStatus.OK).body(magentoMockDataClient.getDefaultCustomizedHomepage());
    }

    @GetMapping(value = "/homepage/categories/{categoryId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<List<GfCategory>> getCategoriesUnderParentId(@PathVariable String categoryId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoHomepageClient.getCategoriesUnderParentId(categoryId));
        } catch (CustomerUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }
}
