package com.gfashion.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCustomerAddress {
    private Integer id;
    private Integer customer_id;
    private GfCustomerRegion region;
    private Integer region_id;
    private String country_id;
    private List<String> street;
    private String company;
    private String telephone;
    private String fax;
    private String postcode;
    private String city;
    private String firstname;
    private String lastname;
    private String middlename;
    private String prefix;
    private String suffix;
    private String vat_id;
    private Boolean default_shipping;
    private Boolean default_billing;
    private JSONObject extension_attributes;
    private List<Map<String, String>> custom_attributes;
}
