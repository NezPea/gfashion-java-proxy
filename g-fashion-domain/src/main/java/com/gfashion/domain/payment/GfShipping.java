package com.gfashion.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShipping {
    private String cartId;
    private String customerToken;
    private ShippingAddress shippingAddress;
}
