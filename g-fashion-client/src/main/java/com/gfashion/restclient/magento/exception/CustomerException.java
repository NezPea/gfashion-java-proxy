package com.gfashion.restclient.magento.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerException extends Exception{
    private HttpStatus status;
    private String errorMessage;
}
