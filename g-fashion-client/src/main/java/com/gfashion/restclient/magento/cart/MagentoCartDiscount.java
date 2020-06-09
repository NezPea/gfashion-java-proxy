package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartDiscount {

    @SerializedName("discount_data")
    private MagentoCartDiscountData discountData;
    @SerializedName("rule_label")
    private String ruleLabel;
    @SerializedName("rule_id")
    private Integer ruleId;
}
