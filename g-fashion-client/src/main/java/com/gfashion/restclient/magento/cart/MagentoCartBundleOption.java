package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartBundleOption {

    @SerializedName("option_id")
    private Integer optionId;
    @SerializedName("option_qty")
    private Integer optionQty;
    @SerializedName("option_selections")
    private List<Integer> optionSelections;
    @SerializedName("extension_attributes")
    private JSONObject extensionAttributes;
}
