package com.gfashion.magento.exception;

import org.springframework.http.HttpStatus;

public class ShipmentException extends GfException {
    public ShipmentException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
