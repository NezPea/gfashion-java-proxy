package com.gfashion.data;

import java.util.List;

/**
 * @ClassName RegisterResponse
 * @Description TODO
 * @Author zhangyuanbo
 * @Date 2020/05/19  23:45
 * @Version 1.0
 */
public class RegisterResponse {
    /**
     * id : 45
     * group_id : 1
     * default_billing : 15
     * default_shipping : 15
     * created_at : 2020-05-19 14:43:44
     * updated_at : 2020-05-19 14:43:44
     * created_in : Default Store View
     * email : jdoe1011110@example.com
     * firstname : Jane100
     * lastname : Doe
     * store_id : 1
     * website_id : 1
     * addresses : [{"id":15,"customer_id":45,"region":{"region_code":"NY","region":"New York","region_id":43},"region_id":43,"country_id":"US","street":["123 Oak Ave"],"telephone":"512-555-1111","postcode":"10756","city":"Purchase","firstname":"Jane100","lastname":"Doe","default_shipping":true,"default_billing":true}]
     * disable_auto_group_change : 0
     * extension_attributes : {"is_subscribed":false}
     */

    private int id;
    private int group_id;
    private String default_billing;
    private String default_shipping;
    private String created_at;
    private String updated_at;
    private String created_in;
    private String email;
    private String firstname;
    private String lastname;
    private int store_id;
    private int website_id;
    private int disable_auto_group_change;
    private ExtensionAttributesBean extension_attributes;
    private List<AddressesBean> addresses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getDefault_billing() {
        return default_billing;
    }

    public void setDefault_billing(String default_billing) {
        this.default_billing = default_billing;
    }

    public String getDefault_shipping() {
        return default_shipping;
    }

    public void setDefault_shipping(String default_shipping) {
        this.default_shipping = default_shipping;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_in() {
        return created_in;
    }

    public void setCreated_in(String created_in) {
        this.created_in = created_in;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getWebsite_id() {
        return website_id;
    }

    public void setWebsite_id(int website_id) {
        this.website_id = website_id;
    }

    public int getDisable_auto_group_change() {
        return disable_auto_group_change;
    }

    public void setDisable_auto_group_change(int disable_auto_group_change) {
        this.disable_auto_group_change = disable_auto_group_change;
    }

    public ExtensionAttributesBean getExtension_attributes() {
        return extension_attributes;
    }

    public void setExtension_attributes(ExtensionAttributesBean extension_attributes) {
        this.extension_attributes = extension_attributes;
    }

    public List<AddressesBean> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressesBean> addresses) {
        this.addresses = addresses;
    }

    public static class ExtensionAttributesBean {
        /**
         * is_subscribed : false
         */

        private boolean is_subscribed;

        public boolean isIs_subscribed() {
            return is_subscribed;
        }

        public void setIs_subscribed(boolean is_subscribed) {
            this.is_subscribed = is_subscribed;
        }
    }

    public static class AddressesBean {
        /**
         * id : 15
         * customer_id : 45
         * region : {"region_code":"NY","region":"New York","region_id":43}
         * region_id : 43
         * country_id : US
         * street : ["123 Oak Ave"]
         * telephone : 512-555-1111
         * postcode : 10756
         * city : Purchase
         * firstname : Jane100
         * lastname : Doe
         * default_shipping : true
         * default_billing : true
         */

        private int id;
        private int customer_id;
        private RegionBean region;
        private int region_id;
        private String country_id;
        private String telephone;
        private String postcode;
        private String city;
        private String firstname;
        private String lastname;
        private boolean default_shipping;
        private boolean default_billing;
        private List<String> street;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public RegionBean getRegion() {
            return region;
        }

        public void setRegion(RegionBean region) {
            this.region = region;
        }

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public boolean isDefault_shipping() {
            return default_shipping;
        }

        public void setDefault_shipping(boolean default_shipping) {
            this.default_shipping = default_shipping;
        }

        public boolean isDefault_billing() {
            return default_billing;
        }

        public void setDefault_billing(boolean default_billing) {
            this.default_billing = default_billing;
        }

        public List<String> getStreet() {
            return street;
        }

        public void setStreet(List<String> street) {
            this.street = street;
        }

        public static class RegionBean {
            /**
             * region_code : NY
             * region : New York
             * region_id : 43
             */

            private String region_code;
            private String region;
            private int region_id;

            public String getRegion_code() {
                return region_code;
            }

            public void setRegion_code(String region_code) {
                this.region_code = region_code;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public int getRegion_id() {
                return region_id;
            }

            public void setRegion_id(int region_id) {
                this.region_id = region_id;
            }
        }
    }
}
