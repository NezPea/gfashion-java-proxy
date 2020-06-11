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

            String data = "{\n" +
                    "  \"amount\": {\n" +
                    "    \"currency\": \"USD\",\n" +
                    "    \"value\": 1000\n" +
                    "  },\n" +
                    "  \"reference\": \"2020061100110011\",\n" +
                    "  \"paymentMethod\": {\n" +
                    "    \"type\": \"scheme\",\n" +
                    "    \"number\": \"4111111111111111\",\n" +
                    "    \"expiryMonth\": \"03\",\n" +
                    "    \"expiryYear\": \"2030\",\n" +
                    "    \"holderName\": \"John Smith\",\n" +
                    "    \"cvc\": \"737\"\n" +
                    "  },\n" +
                    "  \"returnUrl\": \"https://your-company.com/...\",\n" +
                    "  \"merchantAccount\": \"VOICEOFGUOMEDIAINCECOM\"\n" +
                    "}";
            data = "{" +
                    "   \"method\":\"adyen_cc\"," +
                    "   \"additional_data\":{" +
                    "        \"cc_type\":\"VI\"," +
                    "        \"number\":\"4111111111111111\"," +
                    "        \"cvc\":\"737\"," +
                    "        \"expiryMonth\":\"03\"," +
                    "        \"expiryYear\":\"2030\"," +
                    "        \"holderName\":\"John Smith\"," +
                    "        \"store_cc\":false," +
                    "        \"java_enabled\":false," +
                    "        \"screen_color_depth\":24," +
                    "        \"screen_width\":1440," +
                    "        \"screen_height\":900," +
                    "        \"timezone_offset\":-120," +
                    "        \"language\":\"en-US\"" +
                    "    }" +
                    "}";

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
