package com.gfashion.restclient.magento.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartTotalItem {

    private Integer item_id;
    private Integer price;
    private Integer base_price;
    private Integer qty;
    private Integer row_total;
    private Integer base_row_total;
    private Integer row_total_with_discount;
    private Integer tax_amount;
    private Integer base_tax_amount;
    private Integer tax_percent;
    private Integer discount_amount;
    private Integer base_discount_amount;
    private Integer discount_percent;
    private Integer price_incl_tax;
    private Integer base_price_incl_tax;
    private Integer row_total_incl_tax;
    private Integer base_row_total_incl_tax;
    private String options;
    private String name;
}
