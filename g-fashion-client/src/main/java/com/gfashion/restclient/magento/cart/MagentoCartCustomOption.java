package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartCustomOption {

    @SerializedName("option_id")
    private String optionId;
    @SerializedName("option_value")
    private String optionValue;
    @SerializedName("extension_attributes")
    private MagentoCartCustomOptionExtensionAttributes extensionAttributes;
}
