package com.gfashion.restclient;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.sales.GfShipOrder;
import com.gfashion.restclient.magento.exception.*;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.gfashion.restclient.magento.sales.MagentoOrderInfo;
import com.gfashion.restclient.magento.sales.MagentoShipOrder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class MagentoOrderClient {

    @Value("${magento.url.order}")
    private String orderUrl;

    @Autowired
    private RestClient _restClient;

    @Autowired
    private MagentoProductClient magentoProductClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public String shipOrder(Integer orderId, GfShipOrder gfShipOrder) throws OrderNotFoundException, OrderUnknowException {
        String url = orderUrl + orderId + "/ship";
        try {
            validate(gfShipOrder);
            MagentoShipOrder magentoShipOrder = _mapper.convertGfShipOrderToMagentoShipOrder(gfShipOrder);
            ResponseEntity<String> responseEntity = _restClient.postForEntity(url, magentoShipOrder, String.class, null);
//            log.info(responseEntity.getBody());
            String shipmentId = responseEntity.getBody();//example: \"67\"
            shipmentId = shipmentId.substring(1, shipmentId.length() - 1);
            return shipmentId;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new OrderNotFoundException(e.getMessage());
            }
            throw new OrderUnknowException(e.getMessage());
        }
    }

    static void validate(Object object) {
        Set constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
        if (constraintViolations.size() > 0) {
            ConstraintViolation<Object> next = (ConstraintViolation<Object>) constraintViolations.iterator().next();
            throw new ValidationException(next.getPropertyPath() + " " + next.getMessage());
        }
    }


    /**
     *
     */
    public MagentoOrderInfo getOrderInfoByOrderId(Integer orderId) throws CustomerException {
        String url = orderUrl + orderId;

        try {
            ResponseEntity<String> responseEntity = _restClient.exchangeGet(url, String.class, null);
            Gson gson = new Gson();
            JsonObject jsonResp = gson.fromJson( responseEntity.getBody(), JsonObject.class);
            MagentoOrderInfo orderInfo = gson.fromJson( responseEntity.getBody(), MagentoOrderInfo.class);
            JsonObject billingAddress = jsonResp.getAsJsonObject("billing_address");
            JsonObject paymentJson = jsonResp.getAsJsonObject("payment");
            JsonArray itemsArray = jsonResp.getAsJsonArray("items");
            List<GfProduct> gfProductList = getItemInfoByItems(itemsArray);

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return  null;

    }

    /**
     * get product by orderinfo item list
     */
    private List<GfProduct>  getItemInfoByItems(JsonArray itemsArray) throws CustomerException {
        List<GfProduct> gfPList = new ArrayList<>();
        if (itemsArray == null || itemsArray.size() == 0){
            return gfPList;
        }
        Gson gson = new Gson();
        for (Object obj:
             itemsArray) {
            JsonObject jo = gson.fromJson(obj.toString(), JsonObject.class);
            try {
                GfProduct product = magentoProductClient.getProductBySku(jo.get("sku").toString());
                gfPList.add(product);
            }catch (ProductNotFoundException pne){
                throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, pne.getMessage());
            }catch (ProductUnknowException pue){
                throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, pue.getMessage());
            }
        }
        return gfPList;

    }
}

