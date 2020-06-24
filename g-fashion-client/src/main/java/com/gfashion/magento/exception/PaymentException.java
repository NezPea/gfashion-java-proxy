package com.gfashion.magento.exception;

import org.springframework.http.HttpStatus;

public class PaymentException extends GfException {
    public PaymentException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
