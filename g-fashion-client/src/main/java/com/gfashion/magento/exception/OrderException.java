package com.gfashion.magento.exception;

import org.springframework.http.HttpStatus;

public class OrderException extends GfException {
    public OrderException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
