package com.gfashion.api;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.domain.product.GfProductSearchResponseFix;
import com.gfashion.restclient.MagentoProductClient;
import com.gfashion.restclient.magento.exception.ProductException;
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
        } catch (ProductException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
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
            /* 前端url
            http://localhost:8080/gfashion/v1/channelProducts/?category_id,23,eq&price,150,lt&sku,%WJ04%,like&locale=zh
            转换以后
            ?searchCriteria[filter_groups][0][filters][0][field]=category_id&
            searchCriteria[filter_groups][0][filters][0][value]=23&
            searchCriteria[filter_groups][0][filters][0][condition_type]=eq&
            searchCriteria[filter_groups][1][filters][0][field]=price&
            searchCriteria[filter_groups][1][filters][0][value]=150&
            searchCriteria[filter_groups][1][filters][0][condition_type]=lt&
            searchCriteria[filter_groups][2][filters][0][field]=sku&
            searchCriteria[filter_groups][2][filters][0][value]=%WJ04%&
            searchCriteria[filter_groups][2][filters][0][condition_type]=like&
            searchCriteria[filter_groups][3][filters][0][field]=store_id&
            searchCriteria[filter_groups][3][filters][0][value]=localeEn&
            searchCriteria[filter_groups][3][filters][0][condition_type]=eq
            store_id会替换成 application.yml 里边的 magento.url.locale en=1 zh=6

             */
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
                } else if (key.equals("locale")) {
                    String storeId = "1";
                    if (value[0].equals("en")) {
                        storeId = "localeEn";
                    } else if (value[0].equals("zh")) {
                        storeId = "localeZh";
                    }
                    url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][field]=store_id&");
                    url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][value]=").append(storeId).append("&");
                    url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][condition_type]=eq&");
                    i.getAndIncrement();
                    //对于magento search条件 https://devdocs.magento.com/guides/v2.3/rest/performing-searches.html
                    //category_id,23,eq&price,150,lt& 拆分成2个 filter_group ，每个filter_group多个filter
                } else if (key.split(",").length == 3) {
                    if (key.split(",")[0].equals("category_id")) {
                        categoryId.set(Integer.parseInt(key.split(",")[1]));
                    }
                    // 多选支持 color,5487_5477_5485,eq 将颜色代码拆分成多个filters
                    if (key.split(",")[1].contains("_")) {
                        String[] filters = key.split(",")[1].split("_");
                        for (int j = 0; j < filters.length; j++) {
                            url.append("searchCriteria[filter_groups][").append(i).append("][filters][").append(j).append("][field]=").append(key.split(",")[0]).append("&");
                            url.append("searchCriteria[filter_groups][").append(i).append("][filters][").append(j).append("][value]=").append(filters[j]).append("&");
                            url.append("searchCriteria[filter_groups][").append(i).append("][filters][").append(j).append("][condition_type]=").append(key.split(",")[2]).append("&");
                        }
                        i.getAndIncrement();
                    } else {
                        url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][field]=").append(key.split(",")[0]).append("&");
                        url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][value]=").append(key.split(",")[1]).append("&");
                        url.append("searchCriteria[filter_groups][").append(i).append("][filters][0][condition_type]=").append(key.split(",")[2]).append("&");
                        i.getAndIncrement();
                    }
                }
            });
            String resultUrl = url.subSequence(0, url.length() - 1).toString();


            log.info("info:" + resultUrl);
            String magentoSearchCriteria = "";
            return ResponseEntity.status(HttpStatus.OK).body(magentoProductClient.searchProducts(resultUrl, categoryId.intValue()));

        } catch (ProductException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }
}
