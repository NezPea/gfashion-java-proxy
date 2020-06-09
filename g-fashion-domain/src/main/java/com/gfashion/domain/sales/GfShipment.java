package com.gfashion.domain.sales;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @NotNull
    private Integer order_id;

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

    private GfShipmentExtension extension_attributes;


}
