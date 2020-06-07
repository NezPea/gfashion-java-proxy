package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartTotals {

    private Integer grand_total;
    private Integer base_grand_total;
    private Integer subtotal;
    private Integer base_subtotal;
    private Integer discount_amount;
    private Integer base_discount_amount;
    private Integer subtotal_with_discount;
    private Integer base_subtotal_with_discount;
    private Integer shipping_amount;
    private Integer base_shipping_amount;
    private Integer shipping_discount_amount;
    private Integer base_shipping_discount_amount;
    private Integer tax_amount;
    private Integer base_tax_amount;
    private JSONObject weee_tax_applied_amount;
    private Integer shipping_tax_amount;
    private Integer base_shipping_tax_amount;
    private Integer subtotal_incl_tax;
    private Integer shipping_incl_tax;
    private Integer base_shipping_incl_tax;
    private String base_currency_code;
    private String quote_currency_code;
    private Integer items_qty;
    private List<GfCartTotalItem> items;
    private List<GfCartTotalSegment> total_segments;
}
