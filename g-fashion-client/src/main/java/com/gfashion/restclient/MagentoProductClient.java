package com.gfashion.restclient;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.restclient.magento.MagentoProduct;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
public class MagentoProductClient {

    @Value("${magento.url.products}")
    private String productsUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    /**
     * getProductBySku
     * @param skuId
     * @return
     */
    public GfProduct getProductBySku(String skuId){
        ResponseEntity<String> responseEntity = _restClient.exchangeGet(
                productsUrl + skuId,
                String.class,
                null);

        Gson gson = new Gson();
        return _mapper.convertMagentoProductToGfProduct(gson.fromJson(responseEntity.getBody(), MagentoProduct.class));
    }
}