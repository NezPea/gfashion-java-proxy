package com.gfashion.restclient.magento.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartTotalSegmentExtensionAttributes {

    private List<JSONObject> tax_grandtotal_details;
}
