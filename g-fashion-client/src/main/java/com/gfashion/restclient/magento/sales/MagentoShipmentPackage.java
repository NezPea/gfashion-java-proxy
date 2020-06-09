package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentPackage implements Serializable {

    private MagentoShipmentPackageExtension extension_attributes;

}
