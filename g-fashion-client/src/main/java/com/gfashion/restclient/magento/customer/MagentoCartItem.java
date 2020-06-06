package com.gfashion.restclient.magento.customer;

import com.gfashion.domain.cart.GfProductOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartItem {

    private Integer item_id;
    private String sku;
    private Integer qty;
    private String name;
    private Integer price;
    private String product_type;

    /**
     * Cart ID
     */
    private Integer quote_id;
    private GfProductOption product_option;

}
