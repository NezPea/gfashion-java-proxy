package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gfashion.domain.customer.GfCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCart {

    private Integer id;
    private String createdAt;
    private String updatedAt;
    private String convertedAt;
    private Boolean isActive;
    private Boolean isVirtual;
    private List<GfCartItem> items;
    private Integer itemsCount;
    private Integer itemsQty;
    private GfCustomer customer;
    private GfCartAddress billingAddress;
    private String reservedOrderId;
    private Integer origOrderId;
    private GfCartCurrency currency;
    private Boolean customerIsGuest;
    private String customerNote;
    private Boolean customerNoteNotify;
    private Integer customerTaxClassId;
    private Integer storeId;
    private GfCartExtensionAttributes extensionAttributes;
    private GfCartNegotiableQuote negotiableQuote;
    private GfCartAmazonOrderReferenceId amazonOrderReferenceId;
}
