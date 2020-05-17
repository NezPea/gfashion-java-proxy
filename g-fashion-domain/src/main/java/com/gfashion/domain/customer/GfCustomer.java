package com.gfashion.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCustomer {
    private String email;
    private String firstname;
    private String lastname;

    private Integer id;
    /*private Integer group_id;
    private String created_at;
    private String updated_at;
    private String created_in;
    private Integer store_id;
    private Integer website_id;
    // new to create an address class in future
    private String addresses;
    private Integer disable_auto_group_change;
    // need to create an extension attribute in future
    private String extension_attributes;*/
}
