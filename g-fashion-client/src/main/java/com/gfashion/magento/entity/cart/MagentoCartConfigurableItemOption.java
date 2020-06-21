package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartConfigurableItemOption {

    @SerializedName("option_id")
    private String optionId;
    @SerializedName("option_value")
    private String optionValue;
    @SerializedName("extension_attributes")
    private JSONObject extensionAttributes;
}
