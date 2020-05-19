package com.gfashion.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Gf4xx5xxResponse extends GfResponse {

    private String message;
    private List<Error> errors;
    private Integer code;
    private Parameter parameters;
    private String trace;

    @Data
    @JsonInclude(Include.NON_NULL)
    public static class Error {

        private String message;
        private Parameter parameters;
    }

    @Data
    @JsonInclude(Include.NON_NULL)
    public static class Parameter {

        private String resources;
        private String fieldName;
        private String fieldValue;
    }
}


