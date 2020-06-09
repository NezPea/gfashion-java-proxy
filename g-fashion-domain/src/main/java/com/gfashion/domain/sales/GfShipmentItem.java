package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GfShipmentItem implements Serializable {

    /**
     * Additional data.
     */
    private String additionalData;

    /**
     * Description.
     */
    private String description;

    /**
     * Shipment item ID.
     */
    private Integer entityId;

    /**
     * Name.
     */
    private String name;

    /**
     * Parent ID.
     */
    private Integer parentId;

    /**
     * Price.
     */
    private Integer price;

    /**
     * Product ID.
     */
    private Integer productId;

    /**
     * Row total.
     */
    private Integer rowTotal;

    /**
     * SKU.
     */
    private String sku;

    /**
     * Weight.
     */
    private Integer weight;

//    private sales-data-shipment-item-extension-interface extension_attributes;

    /**
     * Order item ID.
     * 必填
     */
//    @NotNull
    private Integer orderItemId;

    /**
     * Quantity.
     * 必填
     */
//    @NotNull
    private Integer qty;
}
