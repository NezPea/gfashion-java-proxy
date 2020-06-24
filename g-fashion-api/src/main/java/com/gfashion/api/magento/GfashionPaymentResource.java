package com.gfashion.api.magento;


import com.gfashion.domain.payment.GfShipping;
import com.gfashion.magento.client.MagentoPaymentClient;
import com.gfashion.magento.exception.CustomerException;
import com.gfashion.magento.exception.PaymentException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
@AllArgsConstructor
public class GfashionPaymentResource {
    private final MagentoPaymentClient magentoPaymentClient;

    /**
     * 获取支付方式
     *
     * @param
     * @return
     */
    @PostMapping("/payment/list")
    public ResponseEntity<String> getPaymentMethods(@RequestBody GfShipping gfShipping) {
        try {
            String paymentMethods = magentoPaymentClient.getPaymentMethods(gfShipping);
            return ResponseEntity.status(HttpStatus.OK).body(paymentMethods);
        } catch (CustomerException | PaymentException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    /**
     * 获取密钥
     *
     * @param customerToken
     * @return
     */
    @GetMapping("/payment/getOriginKey")
    public ResponseEntity<String> getOriginKey(@RequestParam String customerToken) {
        try {
            String originKey = magentoPaymentClient.getOriginKey(customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(originKey);
        } catch (CustomerException | PaymentException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    /**
     * 订单生成
     *
     * @return
     */
    @PostMapping("/payment/information")
    public ResponseEntity<String> paymentInformation(@RequestParam String encryptionData, @RequestParam String customerToken) {
        try {
            String orderId = magentoPaymentClient.createPayment(encryptionData, customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(orderId);
        } catch (CustomerException | PaymentException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    /**
     * 查询支付订单状态
     */
    @GetMapping("/payment/{orderId}/status")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String orderId, @RequestParam String customerToken) {
        try {
            String status = magentoPaymentClient.getPaymentStatus(orderId, customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (CustomerException | PaymentException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    /**
     * 根据订单状态返回是否threeDS2:true,再次调用验证身份指纹识别
     *
     * @return
     */
    @PostMapping("/verificationFingerprint")
    public ResponseEntity<String> verification(@RequestParam String fingerprintData, @RequestParam String customerToken) {
        try {
            String status = magentoPaymentClient.verificationFingerprint(fingerprintData, customerToken);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (CustomerException | PaymentException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

}
