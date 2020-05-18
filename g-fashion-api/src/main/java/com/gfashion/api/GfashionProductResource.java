package com.gfashion.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.gfashion.restclient.MagentoProductClient;

@RestController
@RequestMapping(path = "/gfashion", produces = "application/json")
@AllArgsConstructor
public class GfashionProductResource {
    private MagentoProductClient magentoProductClient;

    @GetMapping("/productdetail/{sku}")
    public String productdetail(@PathVariable("sku") String sku) {
        return magentoProductClient.getProductdetail(sku);
    }
}
