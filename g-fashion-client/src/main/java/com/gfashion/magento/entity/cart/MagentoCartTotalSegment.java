package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartTotalSegment {

    private String code;
    private String title;
    private BigDecimal value;
    @SerializedName("extension_attributes")
    private MagentoCartTotalSegmentExtensionAttributes extensionAttributes;
    private String area;
}
