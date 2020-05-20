package com.gfashion.restclient.magento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoProductLink {

    private String sku;
    private String link_type;
    private String linked_product_sku;
    private String linked_product_type;
    private String position;
}
