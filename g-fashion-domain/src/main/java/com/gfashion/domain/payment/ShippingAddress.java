package com.gfashion.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress {
    private String customerAddressId;
    private String countryId;
    private String regionCode;
    private String region;
    private String customerId;
    private List<String> street;
    private String telephone;
    private String postcode;
    private String city;
    private String firstname;
    private String lastname;
}
