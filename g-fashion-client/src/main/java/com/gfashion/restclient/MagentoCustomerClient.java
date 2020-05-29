package com.gfashion.restclient;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.restclient.magento.customer.MagentoCustomer;
import com.gfashion.restclient.magento.exception.CustomerCreationException;
import com.gfashion.restclient.magento.exception.CustomerNotFoundException;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Component
public class MagentoCustomerClient {

    @Value("${magento.url.customers}")
    private String customersUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public GfCustomer createCustomer(GfCustomerRegistration customerRegistration) throws CustomerCreationException, CustomerUnknowException {

        try{
            ResponseEntity<String> responseEntity = this._restClient.postForEntity(customersUrl, customerRegistration, String.class, null);

            Gson gson = new Gson();
            return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntity.getBody(), MagentoCustomer.class));
        } catch (HttpStatusCodeException e){
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new CustomerCreationException(e.getMessage());
            }
            throw new CustomerUnknowException(e.getMessage());
        }
    }

    public GfCustomer getCustomerById(Integer customerId) throws CustomerUnknowException, CustomerNotFoundException {
        String getCustomerUrl = customersUrl + customerId;

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangeGet(getCustomerUrl, String.class, null);

            Gson gson = new Gson();
            return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntity.getBody(), MagentoCustomer.class));
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new CustomerNotFoundException(e.getMessage());
            }
            throw new CustomerUnknowException(e.getMessage());
        }
    }

    public GfCustomer updateCustomerById(GfCustomerRegistration gfCustomer, Integer customerId) throws CustomerUnknowException, CustomerNotFoundException {
        String getCustomerUrl = customersUrl + customerId;

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangePut(getCustomerUrl, gfCustomer, String.class, null);

            Gson gson = new Gson();
            return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntity.getBody(), MagentoCustomer.class));
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new CustomerNotFoundException(e.getMessage());
            }
            throw new CustomerUnknowException(e.getMessage());
        }
    }
}

