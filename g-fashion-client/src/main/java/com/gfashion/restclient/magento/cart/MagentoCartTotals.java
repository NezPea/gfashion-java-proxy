package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartTotals {

    @SerializedName("grand_total")
    private Integer grandTotal;
    @SerializedName("base_grand_total")
    private Integer baseGrandTotal;
    private Integer subtotal;
    @SerializedName("base_subtotal")
    private Integer baseSubtotal;
    @SerializedName("discount_amount")
    private Integer discountAmount;
    @SerializedName("base_discount_amount")
    private Integer baseDiscountAmount;
    @SerializedName("subtotal_with_discount")
    private Integer subtotalWithDiscount;
    @SerializedName("base_subtotal_with_discount")
    private Integer baseSubtotalWithDiscount;
    @SerializedName("shipping_amount")
    private Integer shippingAmount;
    @SerializedName("base_shipping_amount")
    private Integer baseShippingAmount;
    @SerializedName("shipping_discount_amount")
    private Integer shippingDiscountAmount;
    @SerializedName("base_shipping_discount_amount")
    private Integer baseShippingDiscountAmount;
    @SerializedName("tax_amount")
    private Integer taxAmount;
    @SerializedName("base_tax_amount")
    private Integer baseTaxAmount;
    @SerializedName("weee_tax_applied_amount")
    private JSONObject weeeTaxAppliedAmount;
    @SerializedName("shipping_tax_amount")
    private Integer shippingTaxAmount;
    @SerializedName("base_shipping_tax_amount")
    private Integer baseShippingTaxAmount;
    @SerializedName("subtotal_incl_tax")
    private Integer subtotalInclTax;
    @SerializedName("shipping_incl_tax")
    private Integer shippingInclTax;
    @SerializedName("base_shipping_incl_tax")
    private Integer baseShippingInclTax;
    @SerializedName("base_currency_code")
    private String baseCurrencyCode;
    @SerializedName("quote_currency_code")
    private String quoteCurrencyCode;
    @SerializedName("items_qty")
    private Integer itemsQty;
    private List<MagentoCartTotalItem> items;
    @SerializedName("total_segments")
    private List<MagentoCartTotalSegment> totalSegments;
}
