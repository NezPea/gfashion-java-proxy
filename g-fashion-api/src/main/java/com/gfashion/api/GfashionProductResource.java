package com.gfashion.api;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.restclient.MagentoProductClient;
import com.gfashion.restclient.magento.MagentoSearchCriteria;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.web.bind.annotation.*;

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
    private MagentoProductClient _client;


    @GetMapping("/product/{skuId}")
    public GfProduct getProductBySku(@PathVariable String skuId) {
        return this._client.getProductBySku(skuId);
    }
    /**
     * {@code GET  /channelProduct} : get all the channel products with given query string.
     *
     * @param query query string
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
//    @GetMapping("/channelProduct")
//    public GfProductSearchResponse searchTrxTransactions(@RequestParam(value = "query") String query) {
//        return this._client.searchTrxTransactions(query);
//    }



    @GetMapping(value="/channelProduct",produces="application/json;charset=utf-8")
    public GfProductSearchResponse searchTrxTransactions(HttpServletRequest httpServletRequest) {

        Map<String, String[]> map = httpServletRequest.getParameterMap();
        String queryString ;

        StringBuffer buf = new StringBuffer();
        buf.append("?");
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue()[0];
            buf.append(k).append("=").append(v).append("&");
        }
        queryString = buf.substring(0, buf.length()-1);
        log.info("info:"+ queryString);
        return this._client.searchTrxTransactions(queryString);
    }


}