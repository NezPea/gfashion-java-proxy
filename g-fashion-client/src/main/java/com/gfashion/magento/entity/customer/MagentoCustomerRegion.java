package com.gfashion.magento.entity.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCustomerRegion {
    private String region_code;
    private String region;
    private Integer region_id;
    private JSONObject extension_attributes;
}
