package com.gfashion.restclient;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.restclient.magento.MagentoProduct;
import com.gfashion.restclient.magento.MagentoProductSearchResponse;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MagentoProductClient {

    @Value("${magento.url.products}")
    private String productsUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public GfProduct getProductBySku(String sku){
        String getProductUrl = productsUrl + sku;

        ResponseEntity<String> responseEntityProduct = this._restClient.exchangeGet(getProductUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoProductToGfProduct(gson.fromJson(responseEntityProduct.getBody(), MagentoProduct.class));
    }


    public GfProductSearchResponse searchProducts(String query){
        String getProductSearchUrl = productsUrl + query;

        ResponseEntity<String> responseProductSearch = this._restClient.exchangeGet(getProductSearchUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoProductSearchToGfProductSearch(gson.fromJson(responseProductSearch.getBody(), MagentoProductSearchResponse.class));
    }
}

