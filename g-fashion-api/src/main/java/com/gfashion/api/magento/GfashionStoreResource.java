package com.gfashion.api.magento;

import com.gfashion.api.utility.ExceptionStringFactory;
import com.gfashion.domain.store.GfStore;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.domain.store.GfStoreGroup;
import com.gfashion.domain.store.GfWebsite;
import com.gfashion.magento.client.MagentoStoreClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionStoreResource {

    private MagentoStoreClient _client;

    private ExceptionStringFactory exceptionStringFactory;

    @GetMapping("/stores/storeViews")
    public GfStore[] getStore() {
        try {
            return this._client.getStore();
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatusCode()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/stores/websites")
    public GfWebsite[] getWebsite() {
        try {
            return this._client.getWebsite();
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatusCode()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/stores/storeGroups")
    public GfStoreGroup[] getStoreGroup() {
        try {
            return this._client.getStoreGroup();
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatusCode()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/stores/storeConfigs")
    public GfStoreConfig[] getStoreConfig() {
        try {
            return this._client.getStoreConfig();
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(e.getStatusCode(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatusCode()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exceptionStringFactory.getExceptionStringForStatusCode(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
