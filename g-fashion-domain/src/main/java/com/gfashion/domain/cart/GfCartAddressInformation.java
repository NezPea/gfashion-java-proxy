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

    private GfCartAddress shippingAddress;
    private GfCartAddress billingAddress;
    private String shippingCarrierCode;
    private String shippingMethodCode;
}
