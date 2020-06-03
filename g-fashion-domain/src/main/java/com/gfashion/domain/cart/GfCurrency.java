package com.gfashion.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCurrency {
    private String global_currency_code;
    private String base_currency_code;
    private String store_currency_code;
    private String quote_currency_code;
    private Integer store_to_base_rate;
    private Integer store_to_quote_rate;
    private Integer base_to_global_rate;
    private Integer base_to_quote_rate;
}
