package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartTotalItem {

    private Integer itemId;
    private Integer price;
    private Integer basePrice;
    private Integer qty;
    private Integer rowTotal;
    private Integer baseRowTotal;
    private Integer rowTotalWithDiscount;
    private Integer taxAmount;
    private Integer baseTaxAmount;
    private Integer taxPercent;
    private Integer discountAmount;
    private Integer baseDiscountAmount;
    private Integer discountPercent;
    private Integer priceInclTax;
    private Integer basePriceInclTax;
    private Integer rowTotalInclTax;
    private Integer baseRowTotalInclTax;
    private String options;
    private String name;
}
