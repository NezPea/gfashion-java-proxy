package com.gfashion.restclient.magento.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoShipOrderPayment {

    private String account_status;
    private List<String> additional_information;
    private BigDecimal amount_ordered;
    private BigDecimal base_amount_ordered;
    private BigDecimal base_shipping_amount;
    private String cc_exp_year;
    private String cc_last4;
    private String cc_ss_start_month;
    private String cc_ss_start_year;
    private Integer entity_id;
    private String method;
    private Integer parent_id;
    private BigDecimal shipping_amount;
}
