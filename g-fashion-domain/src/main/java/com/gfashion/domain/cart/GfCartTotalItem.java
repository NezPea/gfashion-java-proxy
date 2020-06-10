package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartTotalItem {

    private Integer itemId;
    private BigDecimal price;
    private BigDecimal basePrice;
    private Integer qty;
    private BigDecimal rowTotal;
    private BigDecimal baseRowTotal;
    private BigDecimal rowTotalWithDiscount;
    private BigDecimal taxAmount;
    private BigDecimal baseTaxAmount;
    private Integer taxPercent;
    private BigDecimal discountAmount;
    private BigDecimal baseDiscountAmount;
    private Integer discountPercent;
    private BigDecimal priceInclTax;
    private BigDecimal basePriceInclTax;
    private BigDecimal rowTotalInclTax;
    private BigDecimal baseRowTotalInclTax;
    private String options;
    private String name;
}
