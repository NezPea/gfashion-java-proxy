package com.gfashion.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCustomer {
    private String email;
    private String firstname;
    private String lastname;

    private Integer id;
    private Integer group_id;
    private String created_at;
    private String updated_at;
    private String created_in;
    private Integer store_id;
    private Integer website_id;
    private List<GfCustomerAddress> addresses;
    private Integer disable_auto_group_change;
    private JSONObject extension_attributes;
}
