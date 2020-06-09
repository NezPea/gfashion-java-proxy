package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartAddress {

    private Integer id;
    private String region;
    private Integer regionId;
    private String regionCode;
    private String countryId;
    private List<String> street;
    private String company;
    private String telephone;
    private String fax;
    private String postcode;
    private String city;
    private String firstName;
    private String lastName;
    private String middleName;
    private String prefix;
    private String suffix;
    private String vatId;
    private Integer customerId;
    private String email;
    private Integer sameAsBilling;
    private Integer customerAddressId;
    private Integer saveInAddressBook;
    private GfCartAddressExtensionAttributes extensionAttributes;
    private List<GfCartCustomAttribute> customAttributes;
}
