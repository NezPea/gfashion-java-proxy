package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAddress {

    private Integer id;
    private String region;
    @SerializedName("region_id")
    private Integer regionId;
    @SerializedName("region_code")
    private String regionCode;
    @SerializedName("country_id")
    private String countryId;
    private List<String> street;
    private String company;
    private String telephone;
    private String fax;
    private String postcode;
    private String city;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("middlename")
    private String middleName;
    private String prefix;
    private String suffix;
    @SerializedName("vat_id")
    private String vatId;
    @SerializedName("customer_id")
    private Integer customerId;
    private String email;
    @SerializedName("same_as_billing")
    private Integer sameAsBilling;
    @SerializedName("customer_address_id")
    private Integer customerAddressId;
    @SerializedName("save_in_address_book")
    private Integer saveInAddressBook;
    @SerializedName("extension_attributes")
    private MagentoCartAddressExtensionAttributes extensionAttributes;
    @SerializedName("custom_attributes")
    private List<MagentoCartCustomAttribute> customAttributes;
}
