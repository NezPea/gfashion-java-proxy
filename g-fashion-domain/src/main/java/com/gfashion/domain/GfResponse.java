package com.gfashion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(Include.NON_NULL)
public class GfResponse {
    @JsonIgnore
    private int statusCode = HttpStatus.OK.value();
}


