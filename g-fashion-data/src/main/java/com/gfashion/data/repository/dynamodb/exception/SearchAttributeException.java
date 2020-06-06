package com.gfashion.data.repository.dynamodb.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchAttributeException extends Exception {
    private String errorMessage;
}

