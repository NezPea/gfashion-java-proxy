package com.gfashion.magento.entity.cart;

import com.gfashion.magento.entity.customer.MagentoCustomer;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCart {

    private Integer id;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("converted_at")
    private String convertedAt;
    @SerializedName("is_active")
    private Boolean isActive;
    @SerializedName("is_virtual")
    private Boolean isVirtual;
    private List<MagentoCartItem> items;
    @SerializedName("items_count")
    private Integer itemsCount;
    @SerializedName("items_qty")
    private Integer itemsQty;
    private MagentoCustomer customer;
    @SerializedName("billing_address")
    private MagentoCartAddress billingAddress;
    @SerializedName("reserved_order_id")
    private String reservedOrderId;
    @SerializedName("orig_order_id")
    private Integer origOrderId;
    private MagentoCartCurrency currency;
    @SerializedName("customer_is_guest")
    private Boolean customerIsGuest;
    @SerializedName("customer_note")
    private String customerNote;
    @SerializedName("customer_note_notify")
    private Boolean customerNoteNotify;
    @SerializedName("customer_tax_class_id")
    private Integer customerTaxClassId;
    @SerializedName("store_id")
    private Integer storeId;
    @SerializedName("extension_attributes")
    private MagentoCartExtensionAttributes extensionAttributes;
    @SerializedName("negotiable_quote")
    private MagentoCartNegotiableQuote negotiableQuote;
    @SerializedName("amazon_order_reference_id")
    private MagentoCartAmazonOrderReferenceId amazonOrderReferenceId;
}
