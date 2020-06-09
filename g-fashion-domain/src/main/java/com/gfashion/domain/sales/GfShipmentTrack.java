package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShipmentTrack implements Serializable {
    /**
     * The order_id for the shipment package.
     * 必填
     */
    private Integer orderId;

    /**
     * Created-at timestamp.
     */
    private String createdAt;

    /**
     * Shipment package ID.
     */
    private Integer entityId;

    /**
     * Parent ID.
     * 必填
     */
    private Integer parentId;

    /**
     * Updated-at timestamp.
     */
    private String updatedAt;

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
    private String trackNumber;

    /**
     * Title.
     * 必填
     */
    private String title;

    /**
     * Carrier code.
     * 必填
     */
    private String carrierCode;
}
