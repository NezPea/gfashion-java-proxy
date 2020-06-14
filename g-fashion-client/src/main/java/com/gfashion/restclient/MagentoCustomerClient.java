package com.gfashion.restclient;

import com.gfashion.domain.customer.*;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public GfCustomer createCustomer(GfCustomerRegistration customerRegistration, String token) throws CustomerException {

        try {
            HttpHeaders tokenHeader = new HttpHeaders();
            tokenHeader.put("Authorization", Arrays.asList((new String[]{token})));

            return this._mapper.convertMagentoCustomerToGfCustomer(
                    this._restClient.postForEntity(customersUrl, customerRegistration, MagentoCustomer.class, tokenHeader).getBody()
            );
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public GfCustomer getCustomerById(Integer customerId, String token) throws CustomerException {
        return verifyCustomerToken(customerId, token);
    }

    public GfCustomer updateCustomerById(GfCustomerRegistration gfCustomer, Integer customerId, String token) throws CustomerException {
        GfCustomer currentCustomer = verifyCustomerToken(customerId, token);

        if (gfCustomer.getCustomer() == null || gfCustomer.getCustomer().getId() == null || gfCustomer.getCustomer().getId() != currentCustomer.getId()) {
            throw new CustomerException(HttpStatus.BAD_REQUEST, "The request body need an id field and the field should be consistant with the path parameter.");
        }

        return updateCustomerWithCustomerToken(gfCustomer.getCustomer(), token);
    }

    public String deleteCustomerById(Integer customerId, String token) throws CustomerException {
        verifyCustomerToken(customerId, token);
        String deleteCustomerUrl = customersUrl + customerId;

        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.put("Authorization", Arrays.asList((new String[]{token})));

        try {
            return this._restClient.exchangeDelete(deleteCustomerUrl, String.class, tokenHeader).getBody();
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

    public GfCustomer addAddress(GfCustomerAddress inputGfCustomerAddress, Integer customerId, String token) throws CustomerException {
        GfCustomer currentCustomer = verifyCustomerToken(customerId, token);

        if (currentCustomer.getAddresses() == null) {
            currentCustomer.setAddresses(new ArrayList<>());
        }

        currentCustomer.getAddresses().add(inputGfCustomerAddress);

        return updateCustomerWithCustomerToken(currentCustomer, token);
    }

    public GfCustomer changeAddress(GfCustomerAddress inputGfCustomerAddress, Integer customerId, String token) throws CustomerException {
        GfCustomer currentCustomer = verifyCustomerToken(customerId, token);

        if (inputGfCustomerAddress.getId() == null) {
            throw new CustomerException(HttpStatus.BAD_REQUEST, "The input customer address's id cannot be empty");
        }

        if (currentCustomer.getAddresses() != null) {
            int totalAddress = currentCustomer.getAddresses().size();
            currentCustomer.setAddresses(
                    currentCustomer.getAddresses().parallelStream().
                            filter(gfCustomerAddress -> inputGfCustomerAddress.getId() != gfCustomerAddress.getId())
                            .collect(Collectors.toList())
            );
            if (totalAddress == currentCustomer.getAddresses().size()) {
                throw new CustomerException(HttpStatus.BAD_REQUEST, "The input customer address's id does not exist in the customer's address list.");
            }
            currentCustomer.getAddresses().add(inputGfCustomerAddress);
        }

        return updateCustomerWithCustomerToken(currentCustomer, token);
    }

    public GfCustomer deleteAddress(Integer customerId, Integer addressId, String token) throws CustomerException {
        GfCustomer currentCustomer = verifyCustomerToken(customerId, token);

        if (currentCustomer.getAddresses() != null) {
            currentCustomer.setAddresses(
                    currentCustomer.getAddresses().parallelStream().
                            filter(gfCustomerAddress -> addressId != gfCustomerAddress.getId())
                            .collect(Collectors.toList())
            );
        }

        return updateCustomerWithCustomerToken(currentCustomer, token);
    }

    public GfCustomer verifyCustomerToken(Integer customerId, String token) throws CustomerException {
        if (token == null) {
            throw new CustomerException(HttpStatus.UNAUTHORIZED, "Please include customer token in the header.");
        }

        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.put("Authorization", Arrays.asList((new String[]{token})));

        try {
            GfCustomer res = this._mapper.convertMagentoCustomerToGfCustomer(
                    this._restClient.exchangeGet(customerMeUrl, MagentoCustomer.class, tokenHeader).getBody()
            );
            if (res.getId() != customerId) {
                throw new CustomerException(HttpStatus.UNAUTHORIZED, "The token passed in id not validate for customer " + customerId);
            }
            return res;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }

            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }

            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private GfCustomer updateCustomerWithCustomerToken(GfCustomer currentCustomer, String token) throws CustomerException {
        try {
            GfCustomerRegistration currentCustomerToMagento = GfCustomerRegistration.builder().customer(currentCustomer).build();

            HttpHeaders tokenHeader = new HttpHeaders();
            tokenHeader.put("Authorization", Arrays.asList((new String[]{token})));

            return this._mapper.convertMagentoCustomerToGfCustomer(
                    this._restClient.exchangePut(customerMeUrl, currentCustomerToMagento, MagentoCustomer.class, tokenHeader).getBody()
            );
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }

            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }

            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}

