package com.gfashion.restclient.magento.sales;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentComment implements Serializable {
    /**
     * Is-customer-notified flag value.
     * 必填
     */
    @SerializedName("is_customer_notified")
    private Integer isCustomerNotified;

    /**
     * Parent ID.
     * 必填
     */
    @SerializedName("parent_id")
    private Integer parentId;

    //    @SerializedName("extension_attributes")
//    private sales-data-shipment-comment-extension-interface extension_attributes;

    /**
     * Comment.
     * 必填
     */
    private String comment;

    /**
     * Is-visible-on-storefront flag value.
     * 必填
     */
    @SerializedName("is_visible_on_front")
    private Integer isVisibleOnFront;

    /**
     * Created-at timestamp.
     */
    @SerializedName("created_at")
    private String createdAt;

    /**
     * Invoice ID.
     */
    @SerializedName("entity_id")
    private Integer entityId;

}
