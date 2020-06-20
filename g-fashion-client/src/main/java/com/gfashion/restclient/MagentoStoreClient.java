package com.gfashion.restclient;

import com.gfashion.domain.store.GfStore;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.domain.store.GfStoreGroup;
import com.gfashion.domain.store.GfWebsite;
import com.gfashion.restclient.magento.MagentoStore;
import com.gfashion.restclient.magento.MagentoStoreConfig;
import com.gfashion.restclient.magento.MagentoStoreGroup;
import com.gfashion.restclient.magento.MagentoWebsite;
import com.gfashion.restclient.magento.mapper.GfMagentoStoreConverter;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MagentoStoreClient {

    @Value("${magento.url.storeViews}")
    private String storeViewsUrl;

    @Value("${magento.url.websites}")
    private String websitesUrl;

    @Value("${magento.url.storeGroups}")
    private String storeGroupsUrl;

    @Value("${magento.url.storeConfigs}")
    private String storeConfigsUrl;

    @Autowired
    private RestClient _restClient;

    private GfMagentoStoreConverter _mapper = Mappers.getMapper(GfMagentoStoreConverter.class);

    public GfStore[] getStore() {
        ResponseEntity<String> responseEntityProduct = this._restClient.exchangeGet(storeViewsUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoStoreToGfStoreArray(gson.fromJson(responseEntityProduct.getBody(), MagentoStore[].class));
    }

    public GfWebsite[] getWebsite() {
        ResponseEntity<String> responseEntityProduct = this._restClient.exchangeGet(websitesUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoWebsiteToGfWebsiteArray(gson.fromJson(responseEntityProduct.getBody(), MagentoWebsite[].class));
    }

    public GfStoreGroup[] getStoreGroup() {
        ResponseEntity<String> responseEntityProduct = this._restClient.exchangeGet(storeGroupsUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoStoreGroupToGfStoreGroupArray(gson.fromJson(responseEntityProduct.getBody(), MagentoStoreGroup[].class));
    }

    public GfStoreConfig[] getStoreConfig() {
        ResponseEntity<String> responseEntityProduct = this._restClient.exchangeGet(storeConfigsUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoStoreConfigToGfStoreConfigArray(gson.fromJson(responseEntityProduct.getBody(), MagentoStoreConfig[].class));
    }
}
