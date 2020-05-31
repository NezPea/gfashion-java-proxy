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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


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

    @GetMapping(value = "/channelProducts", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfProductSearchResponseFix> searchTrxTransactions(HttpServletRequest httpServletRequest) {
        try {
            StringBuilder url = new StringBuilder();
            url.append("?");
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            AtomicInteger i = new AtomicInteger();
            AtomicInteger categoryId = new AtomicInteger();
            parameterMap.forEach((key, value) -> {
                if (key.equals("pageSize")) {
                    url.append("searchCriteria[pageSize]=").append(value[0]).append("&");
                } else if (key.equals("currentPage")) {
                    url.append("searchCriteria[currentPage]=").append(value[0]).append("&");
                } else if (key.equals("sortField")) {
                    url.append("searchCriteria[sortOrders][0][field]=").append(value[0]).append("&");
                } else if (key.equals("sortDirection")) {
                    url.append("searchCriteria[sortOrders][0][direction]=").append(value[0]).append("&");
                } else if (key.split(",").length == 3) {
                    if(key.split(",")[0].equals("category_id")){
                        categoryId.set(Integer.parseInt(key.split(",")[1]));
                    }
                    url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][field]=").append(key.split(",")[0]).append("&");
                    url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][value]=").append(key.split(",")[1]).append("&");
                    url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][condition_type]=").append(key.split(",")[2]).append("&");
                    i.getAndIncrement();
                }
            });
            String resultUrl = url.subSequence(0, url.length() - 1).toString();


            log.info("info:" + resultUrl);
            String magentoSearchCriteria = "";
            return ResponseEntity.status(HttpStatus.OK).body(magentoProductClient.searchProducts(resultUrl,categoryId.intValue()));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (ProductUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }


}
