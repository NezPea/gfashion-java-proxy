package com.gfashion.restclient;

import com.gfashion.domain.homepage.GfCategory;
import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import com.gfashion.restclient.magento.homepage.MagentoCategories;
import com.gfashion.restclient.magento.homepage.MagentoCategory;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MagentoHomepageClient {

    @Value("${magento.url.listCategories}")
    private String listCategories;

    @Value("${magento.url.parameters.parentIdField}")
    private String parentIdField;

    @Value("${magento.url.parameters.field}")
    private String field;

    @Value("${magento.url.parameters.value}")
    private String value;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public List<GfCategory> getCategoriesUnderParentId(String parentId) throws CustomerUnknowException {

        String getDefaultHomepageBrandsUrl = listCategories + "?" +
                String.join("&", new ArrayList<>(Arrays.asList(new String[]{field + parentIdField, value + parentId})));

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangeGet(getDefaultHomepageBrandsUrl, String.class, null);
            Gson gson = new Gson();
            return this._mapper.convertMagentoCategoriesToGfCategories(gson.fromJson(responseEntity.getBody(), MagentoCategories.class).getItems());
        } catch (HttpStatusCodeException e) {
            throw new CustomerUnknowException(e.getMessage());
        }
    }
}
