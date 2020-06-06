package com.gfashion.restclient.magento.sales;

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
    private Integer order_item_id;

    /**
     * Quantity.
     * 必填
     */
    @NotNull
    private Integer qty;

}
