package com.gfashion.restclient.magento.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnauthorizedException extends Exception {
    private String errorMessage;
}
