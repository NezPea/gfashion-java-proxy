package com.gfashion.restclient.magento.sales;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentItemCreation implements Serializable {

    /**
     * Order item ID.
     * 必填
     */
    @NotNull
    @SerializedName("order_item_id")
    private Integer orderItemId;

    /**
     * Quantity.
     * 必填
     */
    @NotNull
    private Integer qty;

}
