package com.gfashion.data;

import java.util.List;

/**
 * @ClassName CustomerEntity
 * @Description TODO 注册用户类
 * @Author IT Miles Chuang
 * @Date 2020/05/18  23:23
 * @Version 1.0
 */
public class CustomerEntity {
    /**
     * customer : {"email":"jdoe100@example.com","firstname":"Jane100","lastname":"Doe","addresses":[{"defaultShipping":true,"defaultBilling":true,"firstname":"Jane100","lastname":"Doe","region":{"regionCode":"NY","region":"New York","regionId":43},"postcode":"10756","street":["123 Oak Ave"],"city":"Purchase","telephone":"512-555-1111","countryId":"US"}]}
     * password : Password1
     */

    private CustomerBean customer;
    private String password;

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class CustomerBean {
        /**
         * email : jdoe100@example.com
         * firstname : Jane100
         * lastname : Doe
         * addresses : [{"defaultShipping":true,"defaultBilling":true,"firstname":"Jane100","lastname":"Doe","region":{"regionCode":"NY","region":"New York","regionId":43},"postcode":"10756","street":["123 Oak Ave"],"city":"Purchase","telephone":"512-555-1111","countryId":"US"}]
         */

        private String email;
        private String firstname;
        private String lastname;
        private List<AddressesBean> addresses;

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

        public List<AddressesBean> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<AddressesBean> addresses) {
            this.addresses = addresses;
        }

        public static class AddressesBean {
            /**
             * defaultShipping : true
             * defaultBilling : true
             * firstname : Jane100
             * lastname : Doe
             * region : {"regionCode":"NY","region":"New York","regionId":43}
             * postcode : 10756
             * street : ["123 Oak Ave"]
             * city : Purchase
             * telephone : 512-555-1111
             * countryId : US
             */

            private boolean defaultShipping=true;
            private boolean defaultBilling=true;
            private String firstname;
            private String lastname;
            private RegionBean region;
            private String postcode;
            private String city;
            private String telephone;
            private String countryId;
            private List<String> street;

            public boolean isDefaultShipping() {
                return defaultShipping;
            }

            public void setDefaultShipping(boolean defaultShipping) {
                this.defaultShipping = defaultShipping;
            }

            public boolean isDefaultBilling() {
                return defaultBilling;
            }

            public void setDefaultBilling(boolean defaultBilling) {
                this.defaultBilling = defaultBilling;
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

            public RegionBean getRegion() {
                return region;
            }

            public void setRegion(RegionBean region) {
                this.region = region;
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

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getCountryId() {
                return countryId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public List<String> getStreet() {
                return street;
            }

            public void setStreet(List<String> street) {
                this.street = street;
            }

            public static class RegionBean {
                /**
                 * regionCode : NY
                 * region : New York
                 * regionId : 43
                 */

                private String regionCode;
                private String region;
                private int regionId;

                public String getRegionCode() {
                    return regionCode;
                }

                public void setRegionCode(String regionCode) {
                    this.regionCode = regionCode;
                }

                public String getRegion() {
                    return region;
                }

                public void setRegion(String region) {
                    this.region = region;
                }

                public int getRegionId() {
                    return regionId;
                }

                public void setRegionId(int regionId) {
                    this.regionId = regionId;
                }
            }
        }
    }
}
