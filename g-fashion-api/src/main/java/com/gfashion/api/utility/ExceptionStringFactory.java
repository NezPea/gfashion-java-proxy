package com.gfashion.api.utility;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
// ensure the return exception message does not contain sensitive information, like user information or implementation details
public class ExceptionStringFactory {

    public String getRuntimeExceptionString() {
        return "Runtime error. Please check backend log or contact backend team.";
    }

    public String getExceptionStringForStatusCode(HttpStatus httpStatus) {
        switch(httpStatus.value()){
            case 400: return "The input is not valid. Please check and correct the input.";
            case 401: return "The request is not authorized to perform the required operation.";
            case 403: return "The requested resource is forbidden for some reason.";
            case 404: return "The required entity cannot be found.";
            case 500: return "Internal error on server. Please check backend log or contact backend team.";
            default: return "The required operation is not succeeded. Please check backend log or contact backend team.";
        }
    }

    public String getExceptionStringForStatusCode(HttpStatus httpStatus, String entity) {
        switch(httpStatus.value()){
            case 400: return "The input " + entity + " is not valid. Please check and correct the input.";
            case 401: return "The " + entity + " is not authorized to perform the required operation.";
            case 403: return "The requested resource is forbidden for some reason.";
            case 404: return "The required " + entity + " cannot be found.";
            case 500: return "Internal error on server. Please check backend log or contact backend team.";
            default: return "The required operation is not succeeded. Please check backend log or contact backend team.";
        }
    }
}
