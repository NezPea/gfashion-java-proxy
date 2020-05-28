package com.gfashion.restclient.magento.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoExtensionAttribute {

    private List <Integer> website_ids;
    private List <Object> category_links;
    private List <Object> bundle_product_options;
    private MagentoStockItem stock_item;
    private List <MagentoConfigurableProductOption> configurable_product_options;
    private List <Integer> configurable_product_links;
    private List <Object> downloadable_product_links;
    private List <Object> downloadable_product_samples;
    private List <Object> giftcard_amounts;

}
