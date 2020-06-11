package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MangentoOrderInfoAddress {



     private String address_type;
     private String city;
     private String country_id;
     private Integer customer_address_id;
     private String email;
     private Integer entity_id;
     private String firstname;
     private String lastname;
     private Integer parent_id;
     private String postcode;
     private String region;
     private String region_code;
     private Integer region_id;
     private List<String> street;
     private String telephone;

}
