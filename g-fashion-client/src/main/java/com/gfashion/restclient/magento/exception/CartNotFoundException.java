package com.gfashion.restclient.magento.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartNotFoundException extends Exception {
    private String errorMessage;
}
