package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentCreationArgumentsExtension implements Serializable {

    private String source_code;
}
