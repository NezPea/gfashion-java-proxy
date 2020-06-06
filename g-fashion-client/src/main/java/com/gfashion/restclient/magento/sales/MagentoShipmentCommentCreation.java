package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentCommentCreation implements Serializable {

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

}
