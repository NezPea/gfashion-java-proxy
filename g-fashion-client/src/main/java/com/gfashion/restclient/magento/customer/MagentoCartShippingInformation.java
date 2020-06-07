package com.gfashion.restclient.magento.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoCartShippingInformation {

    private List<MagentoCartPaymentMethod> payment_methods;
    private MagentoCartTotals totals;
}
