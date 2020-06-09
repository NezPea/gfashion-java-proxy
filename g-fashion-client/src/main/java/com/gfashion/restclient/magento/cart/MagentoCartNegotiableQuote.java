package com.gfashion.restclient.magento.cart;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartNegotiableQuote {

    @SerializedName("quote_id")
    private Integer quoteId;
    @SerializedName("is_regular_quote")
    private Boolean isRegularQuote;
    private String status;
    @SerializedName("negotiated_price_type")
    private Integer negotiatedPriceType;
    @SerializedName("negotiated_price_value")
    private Integer negotiatedPriceValue;
    @SerializedName("shipping_price")
    private Integer shippingPrice;
    @SerializedName("quote_name")
    private String quoteName;
    @SerializedName("expiration_period")
    private String expirationPeriod;
    @SerializedName("email_notification_status")
    private Integer emailNotificationStatus;
    @SerializedName("has_unconfirmed_changes")
    private Boolean hasUnconfirmedChanges;
    @SerializedName("is_shipping_tax_changed")
    private Boolean isShippingTaxChanged;
    @SerializedName("is_customer_price_changed")
    private Boolean isCustomerPriceChanged;
    private Integer notifications;
    @SerializedName("applied_rule_ids")
    private String appliedRuleIds;
    @SerializedName("is_address_draft")
    private Boolean isAddressDraft;
    @SerializedName("deleted_sku")
    private String deletedSku;
    @SerializedName("creator_id")
    private Integer creatorId;
    @SerializedName("creatorType")
    private Integer creatorType;
    @SerializedName("original_total_price")
    private Integer originalTotalPrice;
    @SerializedName("base_original_total_price")
    private Integer baseOriginalTotalPrice;
    @SerializedName("negotiated_total_price")
    private Integer negotiatedTotalPrice;
    @SerializedName("base_negotiated_total_price")
    private Integer baseNegotiatedTotalPrice;
    @SerializedName("extension_attributes")
    private JSONObject extensionAttributes;
}
