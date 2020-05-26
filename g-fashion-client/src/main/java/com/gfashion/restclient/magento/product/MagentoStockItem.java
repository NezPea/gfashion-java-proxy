package com.gfashion.restclient.magento.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoStockItem {
    private Integer item_id;
    private Integer product_id;
    private Integer stock_id;
    private Integer qty;
    private Boolean is_in_stock;
    private Boolean is_qty_decimal;
    private Boolean show_default_notification_message;
    private Boolean use_config_min_qty;
    private Integer min_qty;
    private Integer use_config_min_sale_qty;
    private Integer min_sale_qty;
    private Boolean use_config_max_sale_qty;
    private Integer max_sale_qty;
    private Boolean use_config_backorders;
    private Integer backorders;
    private Boolean use_config_notify_stock_qty;
    private Integer notify_stock_qty;
    private Boolean use_config_qty_increments;
    private Integer qty_increments;
    private Boolean use_config_enable_qty_inc;
    private Boolean enable_qty_increments;
    private Boolean use_config_manage_stock;
    private Boolean manage_stock;
    private String low_stock_date;
    private Boolean is_decimal_divided;
    private Integer stock_status_changed_auto;
    private JSONObject extension_attributes;
}
