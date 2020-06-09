package com.gfashion.restclient;

import com.gfashion.domain.sales.GfShipOrder;
import com.gfashion.restclient.magento.exception.CustomerException;
import com.gfashion.restclient.magento.exception.OrderNotFoundException;
import com.gfashion.restclient.magento.exception.OrderUnknowException;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
import com.gfashion.restclient.magento.sales.MagentoShipOrder;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import java.util.Set;

@Component
@Slf4j
public class MagentoOrderClient {

    @Value("${magento.url.order}")
    private String orderUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public String shipOrder(String customerToken, Integer orderId, GfShipOrder gfShipOrder) throws CustomerException, OrderNotFoundException, OrderUnknowException {
        try {
            validate(gfShipOrder);
            String url = orderUrl + orderId + "/ship";
            HttpHeaders headers = _restClient.getCustomerHeaders(customerToken);
            MagentoShipOrder magentoShipOrder = _mapper.convertGfShipOrderToMagentoShipOrder(gfShipOrder);
            ResponseEntity<String> responseEntity = _restClient.postForEntity(url, magentoShipOrder, String.class, headers);
//            log.info(responseEntity.getBody());
            String shipmentId = responseEntity.getBody();//example: \"67\"
            shipmentId = shipmentId.substring(1, shipmentId.length() - 1);
            return shipmentId;
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new OrderNotFoundException(e.getMessage());
            } else {
                throw new OrderUnknowException(e.getMessage());
            }
        }
    }

    static void validate(Object object) {
        Set constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
        if (constraintViolations.size() > 0) {
            ConstraintViolation<Object> next = (ConstraintViolation<Object>) constraintViolations.iterator().next();
            throw new ValidationException(next.getPropertyPath() + " " + next.getMessage());
        }
    }
}

