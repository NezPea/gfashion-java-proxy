package com.gfashion.restclient;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.restclient.magento.MagentoCustomer;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MagentoCustomerClient {

    @Value("${magento.url.customers}")
    private String customersUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);
    
    public GfCustomer createCustomer(GfCustomerRegistration customerRegistration){
        ResponseEntity<String> responseEntity = this._restClient.postForEntity(customersUrl, customerRegistration, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntity.getBody(), MagentoCustomer.class));
    }

    public GfCustomer getCustomerById(Integer customerId){
        String getCustomerUrl = customersUrl + customerId;

        ResponseEntity<String> responseEntityProduct = this._restClient.exchangeGet(getCustomerUrl, String.class, null);

        Gson gson = new Gson();
        return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntityProduct.getBody(), MagentoCustomer.class));
    }
}

