package com.gfashion.restclient.magento.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCustomerAddress {
    private Integer id;
    private Integer customer_id;
    private JSONObject region;
    private Integer region_id;
    private String country_id;
    private List<String> street;
    private String telephone;
    private String postcode;
    private String city;
    private String firstname;
    private String lastname;
    private Boolean default_shipping;
    private Boolean default_billing;
}
