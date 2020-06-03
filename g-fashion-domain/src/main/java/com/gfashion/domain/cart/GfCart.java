package com.gfashion.domain.cart;

import com.gfashion.domain.customer.GfCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCart {
    private Integer id;
    private String created_at;
    private String updated_at;
    private String is_activte;
    private String is_virtual;
    private String items_count;
    private String items_qty;
    private GfCustomer customer;
    private GfBillingAddress billing_address;
    private String reserved_order_id;
    private Integer orig_order_id;
    private GfCurrency currency;
    private Boolean customer_is_guest;
    private Boolean customer_note_notify;
    private Integer customer_tax_class_id;
    private Integer store_id;
}
