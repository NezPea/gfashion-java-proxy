package com.gfashion.domain.sales;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gfashion.domain.cart.GfCartAddress;
import com.gfashion.domain.product.GfProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GfShipOrderInfo implements Serializable {

    /**
     *
     */
    private String base_currency_code;

    /**
     *
     */
    private BigDecimal base_grand_total;

    /**
     *
     */
    private BigDecimal base_tax_amount;

    /**
     *
     */
    private BigDecimal total_due;

    /**
     *
     */
    private BigDecimal subtotal;

    /**
     *
     */
    private BigDecimal shipping_tax_amount;

    /**
     *
     */
    private String status;

    /**
     *
     */
    private List<GfProduct> gfProductList;

    /**
     *
     */
    private GfCartAddress gfCartAddress;


    /**
     *
     */
    private  List<GfProduct> gfPList;



}
