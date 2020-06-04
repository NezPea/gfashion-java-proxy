package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipment implements Serializable {

    /**
     * Billing address ID.
     */
    private Integer billing_address_id;

    /**
     * Created-at timestamp.
     */
    private String created_at;

    /**
     * Customer ID.
     */
    private Integer customer_id;

    /**
     * Email-sent flag value.
     */
    private Integer email_sent;

    /**
     * Shipment ID.
     */
    private Integer entity_id;

    /**
     * Increment ID.
     */
    private String increment_id;

    /**
     * Order ID.
     * 必填
     */
    private Integer order_id;

    private List<MagentoShipmentPackage> packages;

    /**
     * Shipment status.
     */
    private Integer shipment_status;

    /**
     * Shipping address ID.
     */
    private Integer shipping_address_id;

    /**
     * Shipping label.
     */
    private String shipping_label;

    /**
     * Store ID.
     */
    private Integer store_id;

    /**
     * Total quantity.
     */
    private Integer total_qty;

    /**
     * Total weight.
     */
    private Integer total_weight;

    /**
     * Updated-at timestamp.
     */
    private String updated_at;

    /**
     * 必填
     */
    private List<MagentoShipmentItem> items;

    /**
     * 必填
     */
    private List<MagentoShipmentTrack> tracks;

    /**
     * 必填
     */
    private List<MagentoShipmentComment> comments;

//    private sales-data-shipment-extension-interface extension_attributes;


}
