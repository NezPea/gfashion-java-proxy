package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartNegotiableQuoteItem {

    private Integer itemId;
    private Integer originalPrice;
    private Integer originalTaxAmount;
    private Integer originalDiscountAmount;
    private JSONObject extensionAttributes;
}
