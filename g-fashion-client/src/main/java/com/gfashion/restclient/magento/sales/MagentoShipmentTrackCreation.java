package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentTrackCreation implements Serializable {

//    private shipment-track-creation-extension-interface extension_attributes;

    /**
     * Track number.
     * 必填
     */
    @NotEmpty
    private String track_number;

    /**
     * Title.
     * 必填
     */
    @NotEmpty
    private String title;

    /**
     * Carrier code.
     * 必填
     */
    @NotEmpty
    private String carrier_code;

}
