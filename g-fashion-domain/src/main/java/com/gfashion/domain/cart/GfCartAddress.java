package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartAddress {

    @NonNull
    private String country_id;

    private String postcode;

    private Integer region_id;
    private String region;
    private String region_code;

    private String city;
    private List<String> street;

    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
}
