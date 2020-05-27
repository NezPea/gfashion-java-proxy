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
public class GfCustomer {


    private Integer id;
    private Integer group_id;
    private String default_billing;
    private String default_shipping;
    private String confirmation;
    private String created_at;
    private String updated_at;
    private String created_in;
    private String dob;
    private String email;
    private String firstname;
    private String lastname;
    private String middlename;
    private String prefix;
    private String suffix;
    private Integer gender;
    private Integer store_id;
    private String taxvat;
    private Integer website_id;
    private List<GfCustomerAddress> addresses;
    private Integer disable_auto_group_change;
    private JSONObject extension_attributes;
    private List<Map<String, String>> custom_attributes;
}
