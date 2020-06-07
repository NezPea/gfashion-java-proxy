package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartAddressInformation {

    private GfCartAddress shipping_address;
    private GfCartAddress billing_address;
    private String shipping_carrier_code;
    private String shipping_method_code;
}
