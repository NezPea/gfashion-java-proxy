package com.gfashion.restclient;

import com.gfashion.domain.order.GfOrder;
import com.gfashion.domain.sales.GfShipOrder;
import com.gfashion.restclient.magento.exception.OrderException;
import com.gfashion.restclient.magento.mapper.GfMagentoOrderShipmentConverter;
import com.gfashion.restclient.magento.order.MagentoOrderResp;
import com.gfashion.restclient.magento.sales.MagentoShipOrder;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class MagentoOrderClient {

    @Value("${magento.url.order}")
    private String orderUrl;

    @Value("${magento.url.orders}")
    private String ordersUrl;

    @Value("${magento.url.parameters.field}")
    private String field;

    @Value("${magento.url.parameters.value}")
    private String value;

    @Value("${magento.url.parameters.conditionType}")
    private String conditionType;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoOrderShipmentConverter _mapper = Mappers.getMapper(GfMagentoOrderShipmentConverter.class);

    public String shipOrder(Integer orderId, GfShipOrder gfShipOrder) throws OrderException {
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
            throw new OrderException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new OrderException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    static void validate(Object object) {
        Set constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
        if (constraintViolations.size() > 0) {
            ConstraintViolation<Object> next = (ConstraintViolation<Object>) constraintViolations.iterator().next();
            throw new ValidationException(next.getPropertyPath() + " " + next.getMessage());
        }
    }

    public List<GfOrder> queryOrders(Integer customerId) throws OrderException {
        // Referring to MagentoHomepageClient.java/getCategoriesUnderParentId()
        String filed0 = String.format(field, 0, 0);
        String value0 = String.format(value, 0, 0);
        String conditionType0 = String.format(conditionType, 0, 0);
        String filedFilter = "fields=items[created_at,total_item_count,shipping_amount,status,subtotal,items[order_id]],total_count";

        String url = ordersUrl + "?" +
                String.join("&", new ArrayList<>(Arrays.asList(new String[]{filed0 + "customer_id", value0 + customerId,
                        conditionType0 + "eq", filedFilter})));

        try {
            return _mapper.from(
                    this._restClient.exchangeGet(url, MagentoOrderResp.class, null).getBody().getItems()
            );
        } catch (HttpStatusCodeException e) {
            throw new OrderException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new OrderException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
