package com.gfashion.restclient.magento.cart;

import com.sun.org.apache.xpath.internal.objects.XString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoBillingAddress {
    private Integer id;
    private String region;
    private Integer region_id;
    private String region_code;
    private String country_id;
    private List<String> street;
    private String telephone;
    private String postcode;
    private String city;
    private String firstname;
    private String lastname;
    private Integer customer_id;
    private String email;
    private Integer same_as_billing;
    private Integer customer_address_id;
}
