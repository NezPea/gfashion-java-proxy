package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartDiscountData {

    @SerializedName("amount")
    private Double amount;
    @SerializedName("base_amount")
    private Double base_amount;
    @SerializedName("original_amount")
    private Integer original_amount;
    @SerializedName("base_original_amount")
    private Integer base_original_amount;
}
