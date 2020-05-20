package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductExtensionAttributes {

    private List <Integer> website_ids;
    private List <JSONObject> category_links;
    private List <JSONObject> bundle_product_options;
    private JSONObject stock_item;
    private List <JSONObject> configurable_product_options;
    private List <Integer> configurable_product_links;
    private List <JSONObject> downloadable_product_links;
    private List <JSONObject> downloadable_product_samples;
    private List <JSONObject> giftcard_amounts;
}
