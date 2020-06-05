package com.gfashion.restclient.magento.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartShippingAddressGetException extends Exception {
    private String errorMessage;
}
