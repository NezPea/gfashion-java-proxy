package com.gfashion.restclient.magento.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartDiscountData {

    @SerializedName("amount")
    private BigDecimal amount;
    @SerializedName("base_amount")
    private BigDecimal baseAmount;
    @SerializedName("original_amount")
    private BigDecimal originalAmount;
    @SerializedName("base_original_amount")
    private BigDecimal baseOriginalAmount;
}
