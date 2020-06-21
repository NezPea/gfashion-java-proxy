package com.gfashion.magento.exception;

import org.springframework.http.HttpStatus;

public class ProductException extends GfException {
    public ProductException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
