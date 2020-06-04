package com.gfashion.restclient;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.domain.sales.GfShipment;
import com.gfashion.restclient.magento.customer.MagentoCustomer;
import com.gfashion.restclient.magento.exception.CustomerNotFoundException;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.gfashion.restclient.magento.sales.MagentoShipment;
import com.gfashion.restclient.magento.sales.request.MagentoShipmentReq;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Component
public class MagentoSalesClient {

    @Value("${magento.url.shipment}")
    private String shipmentUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public MagentoShipment saveShipment(MagentoShipmentReq magentoShipmentReq) throws Exception {

        try {
            ResponseEntity<String> responseEntity = this._restClient.postForEntity(shipmentUrl, magentoShipmentReq, String.class, null);
            Gson gson = new Gson();
            return gson.fromJson(responseEntity.getBody(), MagentoShipment.class);
//            return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntity.getBody(), MagentoShipment.class));
//            GfShipment gfShipment = new GfShipment();
//            BeanUtils.copyProperties(magentoShipment, gfShipment);
//            return gfShipment;
        } catch (Exception e) {
            throw e;
        }
    }

    public GfShipment getShipmentById(Integer id) throws Exception {
        String url = shipmentUrl + id;

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangeGet(url, String.class, null);

            Gson gson = new Gson();
            MagentoShipment magentoShipment = gson.fromJson(responseEntity.getBody(), MagentoShipment.class);
            GfShipment gfShipment = new GfShipment();
            BeanUtils.copyProperties(magentoShipment, gfShipment);
            return gfShipment;
//            return GfMagentoConverter.convertMagentoShipmentToGfShipment(gson.fromJson(responseEntity.getBody(), MagentoShipment.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException(e.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public GfCustomer updateCustomerById(GfCustomerRegistration gfCustomer, Integer customerId) throws CustomerUnknowException, CustomerNotFoundException {
        String getCustomerUrl = shipmentUrl + customerId;

        try {
            ResponseEntity<String> responseEntity = this._restClient.exchangePut(getCustomerUrl, gfCustomer, String.class, null);

            Gson gson = new Gson();
            return this._mapper.convertMagentoCustomerToGfCustomer(gson.fromJson(responseEntity.getBody(), MagentoCustomer.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerNotFoundException(e.getMessage());
            }
            throw new CustomerUnknowException(e.getMessage());
        }
    }
}

