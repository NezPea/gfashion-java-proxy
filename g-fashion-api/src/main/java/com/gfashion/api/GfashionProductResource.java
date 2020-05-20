package com.gfashion.api;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.restclient.MagentoProductClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * REST controller for G-Fashion order page
 */
@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
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

        Map<String, String[]> map = httpServletRequest.getParameterMap();

        StringBuilder buf = new StringBuilder();
        buf.append("?");
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue()[0];
            buf.append(k).append("=").append(v).append("&");
        }

        String queryString = buf.substring(0, buf.length() - 1);
        log.info("info:" + queryString);
        return magentoProductClient.searchProducts(queryString);
    }


}