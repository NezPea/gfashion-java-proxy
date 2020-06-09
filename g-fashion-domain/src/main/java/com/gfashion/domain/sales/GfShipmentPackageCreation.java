package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShipmentPackageCreation implements Serializable {
    private GfShipmentPackageExtension extensionAttributes;

}
