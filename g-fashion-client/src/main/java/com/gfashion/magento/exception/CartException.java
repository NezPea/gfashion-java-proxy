package com.gfashion.magento.exception;

import org.springframework.http.HttpStatus;

public class CartException extends GfException {
    public CartException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
