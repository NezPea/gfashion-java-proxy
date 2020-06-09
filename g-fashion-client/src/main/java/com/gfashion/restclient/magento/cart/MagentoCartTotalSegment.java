package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartTotalSegment {

    private String code;
    private String title;
    private Integer value;
    @SerializedName("extension_attributes")
    private MagentoCartTotalSegmentExtensionAttributes extensionAttributes;
    private String area;
}
