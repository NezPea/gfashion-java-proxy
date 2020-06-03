package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShipmentItem implements Serializable {

    /**
     * Additional data.
     */
    private String additional_data;

    /**
     * Description.
     */
    private String description;

    /**
     * Shipment item ID.
     */
    private Integer entity_id;

    /**
     * Name.
     */
    private String name;

    /**
     * Parent ID.
     */
    private Integer parent_id;

    /**
     * Price.
     */
    private Integer price;

    /**
     * Product ID.
     */
    private Integer product_id;

    /**
     * Row total.
     */
    private Integer row_total;

    /**
     * SKU.
     */
    private String sku;

    /**
     * Weight.
     */
    private Integer weight;

//    private sales-data-shipment-item-extension-interface extension_attributes;

    /**
     * Order item ID.
     * 必填
     */
    private Integer order_item_id;

    /**
     * Quantity.
     * 必填
     */
    private Integer qty;
}
