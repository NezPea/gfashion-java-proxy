package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartTotals {

    private BigDecimal grandTotal;
    private BigDecimal baseGrandTotal;
    private BigDecimal subtotal;
    private BigDecimal baseSubtotal;
    private BigDecimal discountAmount;
    private BigDecimal baseDiscountAmount;
    private BigDecimal subtotalWithDiscount;
    private BigDecimal baseSubtotalWithDiscount;
    private BigDecimal shippingAmount;
    private BigDecimal baseShippingAmount;
    private BigDecimal shippingDiscountAmount;
    private BigDecimal baseShippingDiscountAmount;
    private BigDecimal taxAmount;
    private BigDecimal baseTaxAmount;
    private JSONObject weeeTaxAppliedAmount;
    private BigDecimal shippingTaxAmount;
    private BigDecimal baseShippingTaxAmount;
    private BigDecimal subtotalInclTax;
    private BigDecimal shippingInclTax;
    private BigDecimal baseShippingInclTax;
    private String baseCurrencyCode;
    private String quoteCurrencyCode;
    private Integer itemsQty;
    private List<GfCartTotalItem> items;
    private List<GfCartTotalSegment> totalSegments;
}
