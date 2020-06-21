package com.gfashion.magento.entity.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCustomer {

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
    private List<MagentoCustomerAddress> addresses;
    private Integer disable_auto_group_change;
    private MagentoCustomerExtensionAttributes extension_attributes;

}
