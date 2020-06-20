package com.gfashion.restclient.magento.exception;

import org.springframework.http.HttpStatus;

public class HomepageException extends GfException {
    public HomepageException(HttpStatus status, String errorMessage) {
        super(status, errorMessage);
    }
}
