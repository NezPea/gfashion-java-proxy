package com.gfashion.restclient;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.gfashion.restclient.magento.sales.MagentoShipment;
import com.gfashion.restclient.magento.sales.request.MagentoShipmentReq;
import com.google.gson.Gson;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MagentoOrderClient {

    @Value("${magento.url.order}")
    private String orderUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public GfShipment createShipment(GfShipment gfShipment) throws Exception {
        try {
            MagentoShipment magentoShipment = _mapper.from(gfShipment);
            MagentoShipmentReq magentoShipmentReq = new MagentoShipmentReq(magentoShipment);
            ResponseEntity<String> responseEntity = this._restClient.postForEntity(orderUrl, magentoShipmentReq, String.class, null);
            Gson gson = new Gson();
//            return gson.fromJson(responseEntity.getBody(), MagentoShipment.class);
            return this._mapper.from(gson.fromJson(responseEntity.getBody(), MagentoShipment.class));
        } catch (Exception e) {
            throw e;
        }
    }
}

