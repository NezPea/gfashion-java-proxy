package com.gfashion.magento.entity.sales;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("billing_address_id")
    private Integer billingAddressId;

    /**
     * Created-at timestamp.
     */
    @SerializedName("created_at")
    private String createdAt;

    /**
     * Customer ID.
     */
    @SerializedName("customer_id")
    private Integer customerId;

    /**
     * Email-sent flag value.
     */
    @SerializedName("email_sent")
    private Integer emailSent;

    /**
     * Shipment ID.
     */
    @SerializedName("entity_id")
    private Integer entityId;

    /**
     * Increment ID.
     */
    @SerializedName("increment_id")
    private String incrementId;

    /**
     * Order ID.
     * 必填
     */
    @SerializedName("order_id")
    private Integer orderId;

    private List<MagentoShipmentPackage> packages;

    /**
     * Shipment status.
     */
    @SerializedName("shipment_status")
    private Integer shipmentStatus;

    /**
     * Shipping address ID.
     */
    @SerializedName("shipping_address_id")
    private Integer shippingAddressId;

    /**
     * Shipping label.
     */
    @SerializedName("shipping_label")
    private String shippingLabel;

    /**
     * Store ID.
     */
    @SerializedName("store_id")
    private Integer storeId;

    /**
     * Total quantity.
     */
    @SerializedName("total_qty")
    private Integer totalQty;

    /**
     * Total weight.
     */
    @SerializedName("total_weight")
    private Integer totalWeight;

    /**
     * Updated-at timestamp.
     */
    @SerializedName("updated_at")
    private String updatedAt;

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

    //    @SerializedName("extension_attributes")
//    private sales-data-shipment-extension-interface extension_attributes;


}