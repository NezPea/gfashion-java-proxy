package com.gfashion.restclient.magento.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemUnknownException extends Throwable {
    private String errorMessage;
}
