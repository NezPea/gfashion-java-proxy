package com.gfashion.magento.entity.sales;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipmentTrackCreation implements Serializable {

//    @SerializedName("extension_attributes")
//    private shipment-track-creation-extension-interface extension_attributes;

    /**
     * Track number.
     * 必填
     */
    @NotEmpty
    @SerializedName("track_number")
    private String trackNumber;

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
    @SerializedName("carrier_code")
    private String carrierCode;

}
