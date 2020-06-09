package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartTotals {

    private Integer grandTotal;
    private Integer baseGrandTotal;
    private Integer subtotal;
    private Integer baseSubtotal;
    private Integer discountAmount;
    private Integer baseDiscountAmount;
    private Integer subtotalWithDiscount;
    private Integer baseSubtotalWithDiscount;
    private Integer shippingAmount;
    private Integer baseShippingAmount;
    private Integer shippingDiscountAmount;
    private Integer baseShippingDiscountAmount;
    private Integer taxAmount;
    private Integer baseTaxAmount;
    private JSONObject weeeTaxAppliedAmount;
    private Integer shippingTaxAmount;
    private Integer baseShippingTaxAmount;
    private Integer subtotalInclTax;
    private Integer shippingInclTax;
    private Integer baseShippingInclTax;
    private String baseCurrencyCode;
    private String quoteCurrencyCode;
    private Integer itemsQty;
    private List<GfCartTotalItem> items;
    private List<GfCartTotalSegment> totalSegments;
}
