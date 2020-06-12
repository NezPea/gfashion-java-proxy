package com.gfashion.restclient;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerLogin;
import com.gfashion.domain.customer.GfCustomerNewPassword;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.restclient.magento.customer.MagentoCustomer;
import com.gfashion.restclient.magento.exception.*;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;

@Component
public class MagentoCustomerClient {

    @Value("${magento.url.customers}")
    private String customersUrl;

    @Value("${magento.url.customerChangePassword}")
    private String customerChangePasswordUrl;

    @Value("${magento.url.customerMe}")
    private String customerMeUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public String customerLogin(GfCustomerLogin customerLogin) throws CustomerException {
        try {
            ResponseEntity<String> responseEntity = _restClient.getCustomerToken(customerLogin);
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public GfCustomer createCustomer(GfCustomerRegistration customerRegistration) throws CustomerException {

        try {
            return this._mapper.convertMagentoCustomerToGfCustomer(
                    this._restClient.postForEntity(customersUrl, customerRegistration, MagentoCustomer.class, null).getBody()
            );
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public GfCustomer getCustomerById(Integer customerId) throws CustomerException {
        String getCustomerUrl = customersUrl + customerId;

        try {
            return this._mapper.convertMagentoCustomerToGfCustomer(
                    this._restClient.exchangeGet(getCustomerUrl, MagentoCustomer.class, null).getBody()
            );
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public GfCustomer updateCustomerById(GfCustomerRegistration gfCustomer, Integer customerId) throws CustomerException {
        String getCustomerUrl = customersUrl + customerId;

        try {
            return this._mapper.convertMagentoCustomerToGfCustomer(
                    this._restClient.exchangePut(getCustomerUrl, gfCustomer, MagentoCustomer.class, null).getBody()
            );
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public String deleteCustomerById(Integer customerId) throws CustomerException {
        String deleteCustomerUrl = customersUrl + customerId;

        try {
            return this._restClient.exchangeDelete(deleteCustomerUrl, String.class, null).getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Boolean changePassword(GfCustomerNewPassword newPassword, String token) throws CustomerException {
        String changePasswordUrl = customersUrl + customerChangePasswordUrl;

        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.put("Authorization", Arrays.asList((new String[]{token})));

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangePut(changePasswordUrl, newPassword, String.class, tokenHeader);
            return Boolean.valueOf(responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void verifyCustomerToken(String customerId, String token) throws CustomerException {
        if (token == null) {
            throw new CustomerException(HttpStatus.UNAUTHORIZED, "Please include customer token in the header.");
        }

        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.put("Authorization", Arrays.asList((new String[]{token})));

        try {
            GfCustomer res = this._mapper.convertMagentoCustomerToGfCustomer(
                    this._restClient.exchangeGet(customerMeUrl, MagentoCustomer.class, tokenHeader).getBody()
            );
            if (res.getId() != Integer.valueOf(customerId)) {
                throw new CustomerException(HttpStatus.UNAUTHORIZED, "The token passed in id not validate for customer " + customerId);
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}

