package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gfashion.domain.customer.GfCustomerRegion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShippingAddress {

    @NonNull
    private String country_id;
    private String postcode;
    private Integer region_id;
}
