package com.gfashion.restclient.magento.customer;

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
    private MagentoCartTotalSegmentExtensionAttributes extension_attributes;
    private String area;
}
