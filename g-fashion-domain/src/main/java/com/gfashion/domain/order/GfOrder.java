package com.gfashion.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfOrder {
    private String order_id;            //订单号
    private String created_at;          //下单时间
    private Integer total_item_count;   //商品数量
    private Integer shipping_amount;    //包裹数量
    private String status;              //订单状态
    private Integer subtotal;           //订单金额
}
