package com.gfashion.restclient;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.restclient.magento.MagentoCustomer;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MagentoCustomerClient {

    @Autowired
    private RestClient _restClient;

    @Autowired
    private MegentoConfigProperties magentoConfig;

    private GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);
    
    public GfCustomer createCustomer(GfCustomerRegistration customerRegistration){
        String createCustomerUrl = magentoConfig.getUrl().get("customers");

        ResponseEntity<String> responseEntity = this._restClient.postForEntity(createCustomerUrl, customerRegistration, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntity.getBody(), MagentoCustomer.class));
    }

    public GfCustomer getCustomerById(Integer customerId){
        String getCustomerUrl = magentoConfig.getUrl().get("customers") + customerId;

        ResponseEntity<String> responseEntityProduct = this._restClient.exchangeGet(getCustomerUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntityProduct.getBody(), MagentoCustomer.class));
    }
}

