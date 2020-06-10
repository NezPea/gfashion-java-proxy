package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartNegotiableQuoteItem {

    private Integer itemId;
    private BigDecimal originalPrice;
    private BigDecimal originalTaxAmount;
    private BigDecimal originalDiscountAmount;
    private JSONObject extensionAttributes;
}
