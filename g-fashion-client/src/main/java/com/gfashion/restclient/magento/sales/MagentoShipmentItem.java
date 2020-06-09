package com.gfashion.restclient.magento.sales;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentItem implements Serializable {

    /**
     * Additional data.
     */
    @SerializedName("additional_data")
    private String additionalData;

    /**
     * Description.
     */
    private String description;

    /**
     * Shipment item ID.
     */
    @SerializedName("entity_id")
    private Integer entityId;

    /**
     * Name.
     */
    private String name;

    /**
     * Parent ID.
     */
    @SerializedName("parent_id")
    private Integer parentId;

    /**
     * Price.
     */
    private Integer price;

    /**
     * Product ID.
     */
    @SerializedName("product_id")
    private Integer productId;

    /**
     * Row total.
     */
    @SerializedName("row_total")
    private Integer rowTotal;

    /**
     * SKU.
     */
    private String sku;

    /**
     * Weight.
     */
    private Integer weight;

    //    @SerializedName("extension_attributes")
//    private sales-data-shipment-item-extension-interface extension_attributes;

    /**
     * Order item ID.
     * 必填
     */
    @SerializedName("order_item_id")
    private Integer orderItemId;

    /**
     * Quantity.
     * 必填
     */
    private Integer qty;
}
