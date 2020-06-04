package com.gfashion.restclient.magento.customer;

import com.gfashion.domain.cart.GFCartItem;
import com.gfashion.domain.cart.GfCartExtensionAttributes;
import com.gfashion.domain.customer.GfCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCart {

    private Integer id;
    private String created_at;
    private String updated_at;
    private Boolean is_active;
    private Boolean is_virtual;
    private List<GFCartItem> items;
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
