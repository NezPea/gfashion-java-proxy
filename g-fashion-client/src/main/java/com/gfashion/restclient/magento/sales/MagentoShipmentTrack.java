package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentTrack implements Serializable {
    /**
     * The order_id for the shipment package.
     * 必填
     */
    private Integer order_id;

    /**
     * Created-at timestamp.
     */
    private String created_at;

    /**
     * Shipment package ID.
     */
    private Integer entity_id;

    /**
     * Parent ID.
     * 必填
     */
    private Integer parent_id;

    /**
     * Updated-at timestamp.
     */
    private String updated_at;

    /**
     * Weight.
     * 必填
     */
    private Integer weight;

    /**
     * Quantity.
     * 必填
     */
    private Integer qty;

    /**
     * Description.
     * 必填
     */
    private String description;

//    private sales-data-shipment-track-extension-interface extension_attributes;

    /**
     * Track number.
     * 必填
     */
    private String track_number;

    /**
     * Title.
     * 必填
     */
    private String title;

    /**
     * Carrier code.
     * 必填
     */
    private String carrier_code;
}
