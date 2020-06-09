package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartCurrency {

    @SerializedName("global_currency_code")
    private String globalCurrencyCode;
    @SerializedName("base_currency_code")
    private String baseCurrencyCode;
    @SerializedName("store_currency_code")
    private String storeCurrencyCode;
    @SerializedName("quote_currency_code")
    private String quoteCurrencyCode;
    @SerializedName("store_to_base_rate")
    private Integer storeToBaseRate;
    @SerializedName("store_to_quote_rate")
    private Integer storeToQuoteRate;
    @SerializedName("base_to_global_rate")
    private Integer baseToGlobalRate;
    @SerializedName("base_to_quote_rate")
    private Integer baseToQuoteRate;
    @SerializedName("extension_attributes")
    private JSONObject extensionAttributes;
}
