package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartShippingAssignment {

    private MagentoCartShipping shipping;
    private List<MagentoCartItem> items;
    @SerializedName("extension_attributes")
    private JSONObject extensionAttributes;
}
