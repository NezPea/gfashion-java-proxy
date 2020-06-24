package com.gfashion.magento.client;

import com.gfashion.domain.payment.GfShipping;
import com.gfashion.magento.exception.CustomerException;
import com.gfashion.magento.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Component
@Slf4j
public class MagentoPaymentClient {

    @Value("${magento.url.paymentMethods}")
    private String paymentMethodsUrl;

    @Value("${magento.url.carts}")
    private String cartsUrl;

    @Value("${magento.url.originKey}")
    private String originKeyUrl;

    @Value("${magento.url.information}")
    private String informationUrl;

    @Value("${magento.url.paymentStatus}")
    private String paymentStatusUrl;

    @Value(("${magento.url.verificationFingerprint}"))
    private String verificationFingerprintUrl;

    @Autowired
    private RestClient restClient;

    public String getPaymentMethods(GfShipping gfShipping) throws CustomerException, PaymentException {
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(gfShipping.getCustomerToken());
            headers.set("Authorization", "Bearer " + gfShipping.getCustomerToken());

            ResponseEntity<String> responseEntity = restClient.postForEntity(cartsUrl, null, String.class, headers);
            gfShipping.setCartId(String.valueOf(responseEntity.getBody()));
            ResponseEntity<String> responseEntityPaymentMethods = restClient.postForEntity(paymentMethodsUrl, gfShipping, String.class, headers);
            log.info(responseEntityPaymentMethods.getBody());
            return responseEntityPaymentMethods.getBody();

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new PaymentException(e.getStatusCode(), e.getMessage());
        }
    }

    public String getOriginKey(String customerToken) throws CustomerException, PaymentException {
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            headers.set("Authorization", "Bearer " + customerToken);
            ResponseEntity<String> responseEntity = restClient.exchangeGet(originKeyUrl, String.class, headers);
            log.info(responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new PaymentException(e.getStatusCode(), e.getMessage());
        }

    }

    public String createPayment(String encryptionData, String customerToken) throws CustomerException, PaymentException {
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            headers.set("Authorization", "Bearer " + customerToken);
            ResponseEntity<String> responseEntity = restClient.postForEntity(informationUrl, encryptionData, String.class, headers);
            log.info(responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new PaymentException(e.getStatusCode(), e.getMessage());
        }
    }

    public String getPaymentStatus(String orderId, String customerToken) throws CustomerException, PaymentException {
        try {
            String url = String.format(paymentStatusUrl, orderId);
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            headers.set("Authorization", "Bearer " + customerToken);
            ResponseEntity<String> responseEntity = restClient.exchangeGet(url, String.class, headers);
            log.info(responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            throw new PaymentException(e.getStatusCode(), e.getMessage());
        }
    }

    public String verificationFingerprint(String fingerprintData, String customerToken) throws CustomerException, PaymentException {
        try {
            HttpHeaders headers = restClient.getCustomerHeaders(customerToken);
            headers.set("Authorization", "Bearer " + customerToken);
            ResponseEntity<String> responseEntity = restClient.postForEntity(informationUrl, fingerprintData, String.class, headers);
            log.info(responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomerException(e.getStatusCode(), e.getMessage());
            }
            throw new PaymentException(e.getStatusCode(), e.getMessage());
        }
    }

}
