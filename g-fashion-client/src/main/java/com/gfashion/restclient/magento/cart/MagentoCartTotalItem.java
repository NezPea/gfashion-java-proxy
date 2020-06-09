package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartTotalItem {

    @SerializedName("item_id")
    private Integer itemId;
    private BigDecimal price;
    @SerializedName("base_price")
    private BigDecimal basePrice;
    private Integer qty;
    @SerializedName("row_total")
    private Integer rowTotal;
    @SerializedName("base_row_total")
    private Integer baseRowTotal;
    @SerializedName("row_total_with_discount")
    private Integer rowTotalWithDiscount;
    @SerializedName("tax_amount")
    private BigDecimal taxAmount;
    @SerializedName("base_tax_amount")
    private BigDecimal baseTaxAmount;
    @SerializedName("tax_percent")
    private Integer taxPercent;
    @SerializedName("discount_amount")
    private BigDecimal discountAmount;
    @SerializedName("base_discount_amount")
    private BigDecimal baseDiscountAmount;
    @SerializedName("discount_percent")
    private Integer discountPercent;
    @SerializedName("price_incl_tax")
    private BigDecimal priceInclTax;
    @SerializedName("base_price_incl_tax")
    private BigDecimal basePriceInclTax;
    @SerializedName("row_total_incl_tax")
    private BigDecimal rowTotalInclTax;
    @SerializedName("base_row_total_incl_tax")
    private BigDecimal baseRowTotalInclTax;
    private String options;
    private String name;
}
