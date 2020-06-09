package com.gfashion.restclient;

import com.gfashion.restclient.magento.exception.OrderNotFoundException;
import com.gfashion.restclient.magento.exception.OrderUnknowException;
import com.gfashion.restclient.magento.exception.UnauthorizedException;
import com.gfashion.restclient.magento.mapper.GfMagentoConverter;
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
import java.util.Set;

@Component
@Slf4j
public class MagentoOrderClient {

    @Value("${magento.url.order}")
    private String orderUrl;

    @Autowired
    private RestClient _restClient;

    private final GfMagentoConverter _mapper = Mappers.getMapper(GfMagentoConverter.class);

    public String shipOrder(Integer orderId, MagentoShipOrder magentoShipOrder) throws UnauthorizedException, OrderNotFoundException, OrderUnknowException {
        String url = orderUrl + orderId + "/ship";
        try {
            validate(magentoShipOrder);
            ResponseEntity<String> responseEntity = this._restClient.postForEntity(url, magentoShipOrder, String.class, null);
            log.info(responseEntity.getBody());
            return responseEntity.getBody();
//            Gson gson = new Gson();
//            return gson.fromJson(responseEntity.getBody(), MagentoShipment.class);
//            return this._mapper.from(gson.fromJson(responseEntity.getBody(), Object.class));
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException(e.getMessage());
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

