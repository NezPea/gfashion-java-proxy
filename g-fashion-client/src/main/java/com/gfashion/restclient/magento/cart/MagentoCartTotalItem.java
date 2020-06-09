package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartTotalItem {

    @SerializedName("item_id")
    private Integer itemId;
    private Integer price;
    @SerializedName("base_price")
    private Integer basePrice;
    private Integer qty;
    @SerializedName("row_total")
    private Integer rowTotal;
    @SerializedName("base_row_total")
    private Integer baseRowTotal;
    @SerializedName("row_total_with_discount")
    private Integer rowTotalWithDiscount;
    @SerializedName("tax_amount")
    private Integer taxAmount;
    @SerializedName("base_tax_amount")
    private Integer baseTaxAmount;
    @SerializedName("tax_percent")
    private Integer taxPercent;
    @SerializedName("discount_amount")
    private Integer discountAmount;
    @SerializedName("base_discount_amount")
    private Integer baseDiscountAmount;
    @SerializedName("discount_percent")
    private Integer discountPercent;
    @SerializedName("price_incl_tax")
    private Integer priceInclTax;
    @SerializedName("base_price_incl_tax")
    private Integer basePriceInclTax;
    @SerializedName("row_total_incl_tax")
    private Integer rowTotalInclTax;
    @SerializedName("base_row_total_incl_tax")
    private Integer baseRowTotalInclTax;
    private String options;
    private String name;
}
