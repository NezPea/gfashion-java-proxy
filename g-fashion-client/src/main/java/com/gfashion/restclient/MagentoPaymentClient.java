package com.gfashion.restclient;

import com.gfashion.domain.payment.GfAdditonal;
import com.gfashion.restclient.magento.exception.PaymentNotFoundException;
import com.gfashion.restclient.magento.exception.PaymentUnknowException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Component
@Slf4j
public class MagentoPaymentClient {

    @Value("${magento.url.information}")
    private String informationUrl;

    @Value("${magento.url.paymentStatus}")
    private String paymentStatusUrl;

    @Autowired
    private RestClient restClient;

    public String createPayment(GfAdditonal gfAdditonal) throws PaymentNotFoundException, PaymentUnknowException{
        try {
            ResponseEntity<String> responseEntity = restClient.postForEntity(informationUrl, gfAdditonal, String.class, null);
            log.info(responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PaymentNotFoundException(e.getMessage());
            }
            throw new PaymentUnknowException(e.getMessage());
        }
    }

    public String getPaymentStatus(String orderId) throws PaymentNotFoundException, PaymentUnknowException {
        try {
            String url = String.format(paymentStatusUrl, orderId);
            ResponseEntity<String> responseEntity = restClient.exchangeGet(url, String.class, null);
            log.info(responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PaymentNotFoundException(e.getMessage());
            }
            throw new PaymentUnknowException(e.getMessage());
        }
    }

}
