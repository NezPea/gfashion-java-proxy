package com.gfashion.api;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.restclient.MagentoProductClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

        return magentoProductClient.getProductBySku(skuId);
    }

    /**
     * {@code GET  /channelProducts} : get all the channel products with given query string.
     *
     * @param httpServletRequest HttpServletRequest
     * @return the {@link GfProductSearchResponse} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping(value = "/channelProducts", produces = "application/json;charset=utf-8")
    public GfProductSearchResponse searchChannelProducts(HttpServletRequest httpServletRequest) {

        StringBuilder buf = new StringBuilder();
        buf.append("?");
        httpServletRequest.getParameterMap().forEach((k,v)->{
            buf.append(k).append("=").append(v[0]).append("&");
        });
        String queryString = buf.substring(0, buf.length() - 1);
        log.info("info:" + queryString);
        return magentoProductClient.searchProducts(queryString);
    }


}
