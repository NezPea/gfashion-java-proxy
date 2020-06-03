package com.gfashion.api;

import com.gfashion.domain.store.GfStore;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.domain.store.GfStoreGroup;
import com.gfashion.domain.store.GfWebsite;
import com.gfashion.restclient.MagentoStoreClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
@AllArgsConstructor
public class GfashionStoreResource {
    private MagentoStoreClient _client;

    @GetMapping("/stores/storeViews")
    public GfStore[] getStore(){
        return this._client.getStore();
    }

    @GetMapping("/stores/websites")
    public GfWebsite[] getWebsite(){
        return this._client.getWebsite();
    }

    @GetMapping("/stores/storeGroups")
    public GfStoreGroup[] getStoreGroup(){
        return this._client.getStoreGroup();
    }

    @GetMapping("/stores/storeConfigs")
    public GfStoreConfig[] getStoreConfig(){
        return this._client.getStoreConfig();
    }
}
