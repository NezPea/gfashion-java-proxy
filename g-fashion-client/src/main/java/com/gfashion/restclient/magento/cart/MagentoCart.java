package com.gfashion.restclient.magento.cart;

import com.gfashion.restclient.magento.customer.MagentoCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCart {
    private Integer id;
    private String created_at;
    private String updated_at;
    private String is_activte;
    private String is_virtual;
    private String items_count;
    private String items_qty;
    private MagentoCustomer customer;
    private MagentoBillingAddress billing_address;
    private String reserved_order_id;
    private Integer orig_order_id;
    private MagentoCurrency currency;
    private Boolean customer_is_guest;
    private Boolean customer_note_notify;
    private Integer customer_tax_class_id;
    private Integer store_id;
}
