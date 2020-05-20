package com.gfashion.api;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.restclient.MagentoProductClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * REST controller for G-Fashion order page
 */
@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class GfashionProductResource {
    private MagentoProductClient magentoProductClient;

    @GetMapping("/products/{skuId}")
    public GfProduct getProductBySku(@PathVariable String skuId) {
        try{
            return magentoProductClient.getProductBySku(skuId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products Not Found", e);
        }
    }
}
