package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartAmazonOrderReferenceId {

    private String id;
    @SerializedName("amazon_order_reference_id")
    private String amazonOrderReferenceId;
    @SerializedName("quote_id")
    private Integer quoteId;
    @SerializedName("sandbox_simulation_reference")
    private String sandboxSimulationReference;
    private Boolean confirmed;
}
