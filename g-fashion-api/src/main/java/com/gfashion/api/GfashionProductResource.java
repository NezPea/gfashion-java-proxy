package com.gfashion.api;

import com.gfashion.domain.product.GfProduct;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.gfashion.restclient.MagentoProductClient;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = "application/json")
@AllArgsConstructor
public class GfashionProductResource {
    private MagentoProductClient magentoProductClient;

    @GetMapping("/productdetail/{sku}")
    public String productdetail(@PathVariable("sku") String sku) {
        return magentoProductClient.getProductdetail(sku);
    }
    @GetMapping("/product/{sku}")
    public GfProduct getProduct(@PathVariable("sku") String sku) {
        return magentoProductClient.getProduct(sku);
    }
}
