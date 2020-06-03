package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfShipmentComment implements Serializable {
    /**
     * Is-customer-notified flag value.
     * 必填
     */
    private Integer is_customer_notified;

    /**
     * Parent ID.
     * 必填
     */
    private Integer parent_id;

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
    private Integer is_visible_on_front;

    /**
     * Created-at timestamp.
     */
    private String created_at;

    /**
     * Invoice ID.
     */
    private Integer entity_id;

}
