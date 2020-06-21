package com.gfashion.magento.entity.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoOrderItem {
    private String created_at;          //下单时间
    private Integer total_item_count;   //商品数量
    private Integer shipping_amount;    //包裹数量
    private String status;              //订单状态
    private Integer subtotal;           //订单金额
    private List<MagentoOrderItemOrderId> items;    //订单号

}
