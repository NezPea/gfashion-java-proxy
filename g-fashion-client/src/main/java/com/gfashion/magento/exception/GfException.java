package com.gfashion.magento.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfException extends Exception {
    private HttpStatus status;
    private String errorMessage;
}
