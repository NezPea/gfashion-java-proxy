package com.gfashion.api;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.domain.product.GfProductSearchResponseFix;
import com.gfashion.restclient.MagentoProductClient;
import com.gfashion.restclient.magento.exception.ProductNotFoundException;
import com.gfashion.restclient.magento.exception.ProductUnknowException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * REST controller for G-Fashion order page
 */
@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class GfashionProductResource {
    private MagentoProductClient magentoProductClient;


    @GetMapping("/products/{skuId}")
    public ResponseEntity<GfProduct> getProductBySku(@PathVariable String skuId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoProductClient.getProductBySku(skuId));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (ProductUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    /**
     * {@code GET  /channelProducts} : get all the channel products with given query string.
     *
     * @param @PathVariable String query
     * @return the {@link GfProductSearchResponse} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/channelProducts/{query}")
    public ResponseEntity<GfProductSearchResponseFix> searchChannelProducts(@PathVariable String query) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoProductClient.searchProducts(query));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (ProductUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }
}
