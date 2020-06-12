package com.gfashion.api;


import com.gfashion.domain.payment.GfShipping;
import com.gfashion.domain.payment.ShippingAddress;
import com.gfashion.restclient.MagentoCartClient;
import com.gfashion.restclient.MagentoPaymentClient;
import com.gfashion.restclient.magento.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/gfashion/v1/payment", produces = {"application/json"})
@AllArgsConstructor
public class GfashionPaymentResource {
    private final MagentoPaymentClient magentoPaymentClient;

    /**
     * 获取支付方式
     * @param
     * @return
     */
    @PostMapping("/list")
    public ResponseEntity<String> getPaymentMethods(@RequestBody GfShipping gfShipping) {
        try {
            String paymentMethods = magentoPaymentClient.getPaymentMethods(gfShipping);
            return ResponseEntity.status(HttpStatus.OK).body(paymentMethods);
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (PaymentUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }

    }

    /**
     * 获取密钥
     * @param customerToken
     * @return
     */
    @GetMapping("/getOriginKey")
    public ResponseEntity<String> getOriginKey(@RequestParam String customerToken) {
        try {
            String originKey = magentoPaymentClient.getOriginKey(customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(originKey);
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (PaymentUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    /**
     * 订单生成
     * @return
     */
    @PostMapping("/information")
    public ResponseEntity<String> paymentInformation(@RequestParam String encryptionData, @RequestParam String customerToken) {
        try {
            String orderId = magentoPaymentClient.createPayment(encryptionData, customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(orderId);
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
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
    public ResponseEntity<String> getPaymentStatus(@PathVariable String orderId, @RequestParam String customerToken) {
        try {
            String status = magentoPaymentClient.getPaymentStatus(orderId, customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (PaymentUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

    /**
     * 根据订单状态返回是否threeDS2:true,再次调用验证身份指纹识别
     * @return
     */
    @PostMapping("/verificationFingerprint")
    public ResponseEntity<String> verification(@RequestParam String fingerprintData, @RequestParam String customerToken) {
        try {
            String status = magentoPaymentClient.verificationFingerprint(fingerprintData, customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (CustomerException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        } catch (PaymentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
        } catch (PaymentUnknowException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }

}
