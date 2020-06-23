package com.gfashion.magento.entity.sales;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("order_id")
    private Integer orderId;

    /**
     * Created-at timestamp.
     */
    @SerializedName("created_at")
    private String createdAt;

    /**
     * Shipment package ID.
     */
    @SerializedName("entity_id")
    private Integer entityId;

    /**
     * Parent ID.
     * 必填
     */
    @SerializedName("parent_id")
    private Integer parentId;

    /**
     * Updated-at timestamp.
     */
    @SerializedName("updated_at")
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

    //    @SerializedName("extension_attributes")
//    private sales-data-shipment-track-extension-interface extension_attributes;

    /**
     * Track number.
     * 必填
     */
    @SerializedName("track_number")
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
    @SerializedName("carrier_code")
    private String carrierCode;
}
