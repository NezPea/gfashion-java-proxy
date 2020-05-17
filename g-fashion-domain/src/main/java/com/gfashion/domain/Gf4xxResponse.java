package com.gfashion.domain;

import java.util.List;
import lombok.Data;

@Data
public class Gf4xxResponse extends GfResponse{
    private String message;
    private List<Error> errors;
    private Integer code;
    private List<Parameter> parameters;
    private String trace;

    @Data
    public static class Error {
        private String message;
        private List<Parameter> parameters;
    }

    @Data
    public static class Parameter {
        private String resources;
        private String fieldName;
        private String fieldValue;
    }
}


