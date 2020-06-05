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
    private String created_at;
    private String updated_at;
    private Boolean is_active;
    private Boolean is_virtual;
    private List<GfCartItem> items;
    private Integer items_count;
    private Integer items_qty;
    private GfCustomer customer;
    private Integer orig_order_id;
    private Boolean customer_is_guest;
    private Boolean customer_note_notify;
    private Integer customer_tax_class_id;
    private Integer store_id;
    private GfCartExtensionAttributes extension_attributes;

}
