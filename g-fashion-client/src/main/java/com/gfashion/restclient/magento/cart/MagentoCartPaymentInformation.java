package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartPaymentInformation {

    private MagentoCartPaymentMethod paymentMethod;
    @SerializedName("billing_address")
    private MagentoCartAddress billingAddress;
}
