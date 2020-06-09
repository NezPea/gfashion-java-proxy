package com.gfashion.domain.sales;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GfShipmentItemCreation implements Serializable {

    /**
     * Order item ID.
     * 必填
     */
    @NotNull
    private Integer orderItemId;

    /**
     * Quantity.
     * 必填
     */
    @NotNull
    private Integer qty;

}
