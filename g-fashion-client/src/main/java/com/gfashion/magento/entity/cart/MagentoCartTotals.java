package com.gfashion.magento.entity.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartTotals {

    @SerializedName("grand_total")
    private BigDecimal grandTotal;
    @SerializedName("base_grand_total")
    private BigDecimal baseGrandTotal;
    private BigDecimal subtotal;
    @SerializedName("base_subtotal")
    private BigDecimal baseSubtotal;
    @SerializedName("discount_amount")
    private BigDecimal discountAmount;
    @SerializedName("base_discount_amount")
    private BigDecimal baseDiscountAmount;
    @SerializedName("subtotal_with_discount")
    private BigDecimal subtotalWithDiscount;
    @SerializedName("base_subtotal_with_discount")
    private BigDecimal baseSubtotalWithDiscount;
    @SerializedName("shipping_amount")
    private BigDecimal shippingAmount;
    @SerializedName("base_shipping_amount")
    private BigDecimal baseShippingAmount;
    @SerializedName("shipping_discount_amount")
    private BigDecimal shippingDiscountAmount;
    @SerializedName("base_shipping_discount_amount")
    private BigDecimal baseShippingDiscountAmount;
    @SerializedName("tax_amount")
    private BigDecimal taxAmount;
    @SerializedName("base_tax_amount")
    private BigDecimal baseTaxAmount;
    @SerializedName("weee_tax_applied_amount")
    private JSONObject weeeTaxAppliedAmount;
    @SerializedName("shipping_tax_amount")
    private BigDecimal shippingTaxAmount;
    @SerializedName("base_shipping_tax_amount")
    private BigDecimal baseShippingTaxAmount;
    @SerializedName("subtotal_incl_tax")
    private BigDecimal subtotalInclTax;
    @SerializedName("shipping_incl_tax")
    private BigDecimal shippingInclTax;
    @SerializedName("base_shipping_incl_tax")
    private BigDecimal baseShippingInclTax;
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
