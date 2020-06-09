package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartExtensionAttributes {

    @SerializedName("shipping_assignments")
    private List<MagentoCartShippingAssignment> shippingAssignments;
}
