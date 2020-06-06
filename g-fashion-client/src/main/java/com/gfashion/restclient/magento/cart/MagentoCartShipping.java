package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartShipping {

    private MagentoCartAddress address;
    private String method;
    @SerializedName("extension_attributes")
    private JSONObject extensionAttributes;
}
