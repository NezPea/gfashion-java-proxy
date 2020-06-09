package com.gfashion.api.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gfashion.data.repository.elasticsearch.constants.ElasticSearchConstants;
import com.gfashion.data.repository.elasticsearch.handlers.AwsResponse;
import com.gfashion.data.repository.elasticsearch.models.Product;
import com.gfashion.data.repository.elasticsearch.models.ProductQuery;
import com.gfashion.data.repository.elasticsearch.service.ElasticSearchService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for G-Fashion search page
 */
@RestController
@RequestMapping(path = "/gfashion/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class GfashionProductElasticsearchResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GfashionProductElasticsearchResource.class);

    @Autowired
    private ElasticSearchService elasticSearchService;

    @PostMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getFromElasticSearch(@RequestBody final ProductQuery productQuery) {
        return ResponseEntity.status(HttpStatus.OK).body(
                elasticSearchService.getProducts(ElasticSearchConstants.PRODUCTS_INDEX, 0, 100, null, productQuery));
    }

    @PostMapping(value = "/fuzzySearch", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getFromElasticSearchFuzzySearch(@RequestBody final ProductQuery productQuery) {
        return ResponseEntity.status(HttpStatus.OK).body(
                elasticSearchService.getProductsFuzzySearch(ElasticSearchConstants.PRODUCTS_INDEX, 0, 100, null, productQuery));
    }

    @PostMapping(value = "/create", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> createElasticSearchObject(@RequestBody final Product product) {
        String title = null;
        try {
            title = elasticSearchService.createNewProduct(product);
            if (title != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Successfully created " + title);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to create Product.", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create  " + product.getName());
    }

    @DeleteMapping(value = "/delete", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> deleteFromElasticSearch(@RequestParam("index") final String index,
                                                          @RequestParam("type") final String type,
                                                          @RequestParam("id") final String id) {
        AwsResponse response = elasticSearchService.deleteDocument(index, type, id);
        if (response != null && response.getHttpResponse().getStatusCode() == 200) {
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted product with ID of " + id);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting ElasticSearch document");
        }
    }

    @GetMapping(value = "/statistics", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> indexStatistics(@RequestParam("index") final String index) {
        String response = elasticSearchService.getIndexStatistics(index);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching statistics for index");
        }
    }
}
