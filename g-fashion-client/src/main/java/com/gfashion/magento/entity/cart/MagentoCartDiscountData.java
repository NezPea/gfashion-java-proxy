package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
