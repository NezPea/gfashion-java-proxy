package com.gfashion.restclient.magento.sales;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentPackage implements Serializable {
    @SerializedName("extension_attributes")
    private MagentoShipmentPackageExtension extensionAttributes;

}
