package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAddressInformation {

    @SerializedName("shipping_address")
    private MagentoCartAddress shippingAddress;
    @SerializedName("billing_address")
    private MagentoCartAddress billingAddress;
    @SerializedName("shipping_carrier_code")
    private String shippingCarrierCode;
    @SerializedName("shipping_method_code")
    private String shippingMethodCode;
}
