package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShipment implements Serializable {

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
//    @NotNull
    private Integer orderId;

    private List<GfShipmentPackage> packages;

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
    @NotEmpty
    private List<GfShipmentItem> items;

    /**
     * 必填
     */
    private List<GfShipmentTrack> tracks;

    /**
     * 必填
     */
    private List<GfShipmentComment> comments;

//    private sales-data-shipment-extension-interface extension_attributes;


}
