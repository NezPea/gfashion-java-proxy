package com.gfashion.api;


import com.gfashion.domain.payment.GfAdditonal;
import com.gfashion.restclient.MagentoPaymentClient;
import com.gfashion.restclient.magento.exception.PaymentNotFoundException;
import com.gfashion.restclient.magento.exception.PaymentUnknowException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(path = "/gfashion/v1/payment", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionPaymentResource {
    private MagentoPaymentClient magentoPaymentClient;

    /**
     * 支付订单生成
     * @return
     */
    @PostMapping("/information")
    public ResponseEntity<String> paymentInformation(@RequestBody GfAdditonal gfAdditonal) {
//        GfAdditonal gfAdditonal = new GfAdditonal();
//        gfAdditonal.setMethod("adyen_cc");
//        GfAdditionalData gfAdditionalData = new GfAdditionalData();
//        gfAdditionalData.setCc_type("VI");
//        gfAdditionalData.setNumber("4111111111111111");
//        gfAdditionalData.setCvc("737");
//        gfAdditionalData.setExpiryMonth("03");
//        gfAdditionalData.setExpiryYear("2030");
//        gfAdditionalData.setHolderName("John Smith");
//        gfAdditonal.setGfAdditionalData(gfAdditionalData);
        try {
            String orderId = magentoPaymentClient.createPayment(gfAdditonal);
            return ResponseEntity.status(HttpStatus.OK).body(orderId);
        } catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (PaymentUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    /**
     * 查询支付订单状态
     */
    @GetMapping("/{orderId}/status")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String orderId) {
        try {
            String status = magentoPaymentClient.getPaymentStatus(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (PaymentUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

}
