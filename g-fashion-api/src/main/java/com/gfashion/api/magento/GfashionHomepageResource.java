package com.gfashion.api.magento;

import com.gfashion.api.utility.ExceptionStringFactory;
import com.gfashion.domain.homepage.CustomizedHomepage;
import com.gfashion.domain.homepage.GfCategory;
import com.gfashion.domain.homepage.GfCountry;
import com.gfashion.magento.client.MagentoHomepageClient;
import com.gfashion.magento.client.MagentoMockDataClient;
import com.gfashion.magento.exception.HomepageException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class GfashionHomepageResource {

    private MagentoMockDataClient magentoMockDataClient;

    private MagentoHomepageClient magentoHomepageClient;

    private ExceptionStringFactory exceptionStringFactory;

    @GetMapping(value = "/homepage", produces = "application/json;charset=utf-8")
    public ResponseEntity<CustomizedHomepage> getDefaultCustomizedHomepage() {
        return ResponseEntity.status(HttpStatus.OK).body(magentoMockDataClient.getDefaultCustomizedHomepage());
    }

    @GetMapping(value = "/homepage/categories/{categoryId}", produces = "application/json;charset=utf-8")
    @Cacheable("categories")
    public ResponseEntity<List<GfCategory>> getCategoriesUnderParentId(@PathVariable String categoryId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoHomepageClient.getCategoriesUnderParentId(categoryId));
        } catch (HomepageException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus()));
        }
    }

    @GetMapping(value = "/homepage/categories", produces = "application/json;charset=utf-8")
    @Cacheable("categories")
    public ResponseEntity<List<GfCategory>> getCategories(@RequestParam(required = true) @Min(1) Integer fromLevel, @RequestParam(required = true) @Min(1) @Max(10) Integer toLevel, @RequestParam(required = false, defaultValue = "en") String locale) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoHomepageClient.getCategories(fromLevel, toLevel, locale));
        } catch (HomepageException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping(value = "/homepage/countries", produces = "application/json;charset=utf-8")
    @Cacheable("countries")
    public ResponseEntity<List<GfCountry>> getCountries() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoHomepageClient.getCountries());
        } catch (HomepageException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.BAD_REQUEST));
        }
    }
}
