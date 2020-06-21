package com.gfashion.magento.exception;

import org.springframework.http.HttpStatus;

public class CustomerException extends GfException {
    public CustomerException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
