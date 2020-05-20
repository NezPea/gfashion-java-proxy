package com.gfashion.api;

import com.gfashion.domain.product.GfProduct;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.gfashion.restclient.MagentoProductClient;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/gfashion/v1", produces = "application/json")
@AllArgsConstructor
public class GfashionProductResource {
    private MagentoProductClient magentoProductClient;

    @GetMapping("/productdetail/{sku}")
    public String productdetail(@PathVariable("sku") String sku) {
        try{
            return magentoProductClient.getProductdetail(sku);
        }catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Products Not Found", e);
        }
    }

    @GetMapping("/products/{skuId}")
    public GfProduct getProductBySku(@PathVariable("skuId") String skuId) {
        try{
            return magentoProductClient.getProductBySku(skuId);
        }catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Products Not Found", e);
        }
    }
}
