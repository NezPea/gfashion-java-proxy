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
public class GfCartNegotiableQuote {

    private Integer quoteId;
    private Boolean isRegularQuote;
    private String status;
    private Integer negotiatedPriceType;
    private Integer negotiatedPriceValue;
    private BigDecimal shippingPrice;
    private String quoteName;
    private String expirationPeriod;
    private Integer emailNotificationStatus;
    private Boolean hasUnconfirmedChanges;
    private Boolean isShippingTaxChanged;
    private Boolean isCustomerPriceChanged;
    private Integer notifications;
    private String appliedRuleIds;
    private Boolean isAddressDraft;
    private String deletedSku;
    private Integer creatorId;
    private Integer creatorType;
    private BigDecimal originalTotalPrice;
    private BigDecimal baseOriginalTotalPrice;
    private BigDecimal negotiatedTotalPrice;
    private BigDecimal baseNegotiatedTotalPrice;
    private JSONObject extensionAttributes;
}
