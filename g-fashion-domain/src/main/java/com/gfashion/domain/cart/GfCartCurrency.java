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
public class GfCartCurrency {

    private String globalCurrencyCode;
    private String baseCurrencyCode;
    private String storeCurrencyCode;
    private String quoteCurrencyCode;
    private Integer storeToBaseRate;
    private Integer storeToQuoteRate;
    private Integer baseToGlobalRate;
    private Integer baseToQuoteRate;
    private JSONObject extensionAttributes;
}
