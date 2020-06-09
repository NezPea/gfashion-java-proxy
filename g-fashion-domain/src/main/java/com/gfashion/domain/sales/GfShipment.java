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
    private Integer billingAddressId;

    /**
     * Created-at timestamp.
     */
    private String createdAt;

    /**
     * Customer ID.
     */
    private Integer customerId;

    /**
     * Email-sent flag value.
     */
    private Integer emailSent;

    /**
     * Shipment ID.
     */
    private Integer entityId;

    /**
     * Increment ID.
     */
    private String incrementId;

    /**
     * Order ID.
     * 必填
     */
    @NotNull
    private Integer orderId;

    private List<GfShipmentPackage> packages;

    /**
     * Shipment status.
     */
    private Integer shipmentStatus;

    /**
     * Shipping address ID.
     */
    private Integer shippingAddressId;

    /**
     * Shipping label.
     */
    private String shippingLabel;

    /**
     * Store ID.
     */
    private Integer storeId;

    /**
     * Total quantity.
     */
    private Integer totalQty;

    /**
     * Total weight.
     */
    private Integer totalWeight;

    /**
     * Updated-at timestamp.
     */
    private String updatedAt;

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

    private GfShipmentExtension extensionAttributes;


}
