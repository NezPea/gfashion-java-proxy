package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartItemProductOption {

    @SerializedName("extension_attributes")
    private MagentoCartItemProductOptionExtensionAttributes extensionAttributes;
}
