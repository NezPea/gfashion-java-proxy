package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoOrderInfoItem {

        private Integer amount_refunded;
        private String applied_rule_ids;
        private BigDecimal base_amount_refunded;
        private BigDecimal base_discount_amount;
        private BigDecimal base_discount_invoiced;
        private BigDecimal base_discount_tax_compensation_amount;
        private BigDecimal base_original_price;
        private BigDecimal base_price;
        private BigDecimal base_price_incl_tax;
        private BigDecimal base_row_invoiced;
        private BigDecimal base_row_total;
        private BigDecimal base_row_total_incl_tax;
        private BigDecimal base_tax_amount;
        private BigDecimal base_tax_invoiced;
//        private String created_at": "2020-05-17 11:55:31",
        private BigDecimal discount_amount;
        private BigDecimal discount_invoiced;
        private BigDecimal discount_percent;
        private BigDecimal free_shipping;
        private BigDecimal discount_tax_compensation_amount;
        private BigDecimal discount_tax_compensation_canceled;
        private BigDecimal is_qty_decimal;
        private Integer is_virtual;
        private Integer item_id;
        private String name;
        private Integer no_discount;
        private Integer order_id;
        private BigDecimal original_price;
        private BigDecimal price;
        private BigDecimal price_incl_tax;
        private Integer product_id;
        private String product_type;
        private Integer qty_canceled;
        private Integer qty_invoiced;
        private Integer qty_ordered;
        private Integer qty_refunded;
        private Integer qty_shipped;
        private Integer quote_item_id;
        private Integer row_invoiced;
        private BigDecimal row_total;
        private BigDecimal row_total_incl_tax;
        private BigDecimal row_weight;
        private String sku;
        private Integer store_id;
        private BigDecimal tax_amount;
        private BigDecimal tax_canceled;
        private BigDecimal tax_invoiced;
        private BigDecimal tax_percent;
//        private String updated_at": "2020-06-06 19:24:30",
        private BigDecimal weight;
}
