package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoOrderInfo {


    private Integer applied_rule_ids;
    private String base_currency_code;
    private BigDecimal base_discount_amount;
    private BigDecimal base_discount_canceled;
    private BigDecimal base_grand_total;
    private BigDecimal base_discount_tax_compensation_amount;
    private BigDecimal base_shipping_amount;
    private BigDecimal base_shipping_canceled;
    private BigDecimal base_shipping_discount_amount;
    private BigDecimal base_shipping_discount_tax_compensation_amnt;
    private BigDecimal base_shipping_incl_tax;
    private BigDecimal base_shipping_tax_amount;
    private BigDecimal base_subtotal;
    private BigDecimal base_subtotal_canceled;
    private BigDecimal base_subtotal_incl_tax;
    private BigDecimal base_tax_amount;
    private BigDecimal base_tax_canceled;
    private BigDecimal base_total_canceled;
    private BigDecimal base_total_due;
    private Integer base_to_global_rate;
    private Integer base_to_order_rate;
    private Integer billing_address_id;
    //    private String created_at": "2020-05-17 11:55:31",
    private String customer_email;
    private String customer_firstname;
    private Integer customer_group_id;
    private Integer customer_id;
    private Integer customer_is_guest;
    private String customer_lastname;
    private Integer customer_note_notify;
    private BigDecimal discount_amount;
    private BigDecimal discount_canceled;
    private Integer entity_id;
    private String global_currency_code;
    private BigDecimal grand_total;
    private BigDecimal discount_tax_compensation_amount;
    private String increment_id;
    private Integer is_virtual;
    private String order_currency_code;
    private String protect_code;
    private Integer quote_id;
    private String remote_ip;
    private BigDecimal shipping_amount;
    private BigDecimal shipping_canceled;
    private String shipping_description;
    private BigDecimal shipping_discount_amount;
    private BigDecimal shipping_discount_tax_compensation_amount;
    private BigDecimal shipping_incl_tax;
    private BigDecimal shipping_tax_amount;
    private String state;
    private String status;
    private String store_currency_code;
    private Integer store_id;
    private String store_name;
    private BigDecimal store_to_base_rate;
    private BigDecimal store_to_order_rate;
    private BigDecimal subtotal;
    private BigDecimal subtotal_canceled;
    private BigDecimal subtotal_incl_tax;
    private BigDecimal tax_amount;
    private BigDecimal tax_canceled;
    private BigDecimal total_canceled;
    private BigDecimal total_due;
    private BigDecimal total_item_count;
    private BigDecimal total_qty_ordered;
    //    private String updated_at": "2020-06-06 19:24:30",
    private BigDecimal weight;

    private List<MagentoOrderInfoItem> items;

    private MagentoShipOrderPayment payment;

    private MangentoOrderInfoAddress billing_address;

}