package com.gfashion.api.magento;

import com.gfashion.api.utility.ExceptionStringFactory;
import com.gfashion.domain.order.GfOrder;
import com.gfashion.domain.sales.GfShipOrder;
import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.GfShipmentTrack;
import com.gfashion.domain.sales.response.GfShipmentResp;
import com.gfashion.magento.client.MagentoOrderClient;
import com.gfashion.magento.client.MagentoShipmentClient;
import com.gfashion.magento.exception.OrderException;
import com.gfashion.magento.exception.ShipmentException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "/gfashion/v1/orders", produces = {"application/json"})
@AllArgsConstructor
public class GfashionOrderResource {

    private MagentoOrderClient magentoOrderClient;

    private MagentoShipmentClient magentoShipmentClient;

    private ExceptionStringFactory exceptionStringFactory;

    @PostMapping(value = "/{orderId}/ship")
    public ResponseEntity<GfShipment> shipOrder(@PathVariable Integer orderId, @RequestBody GfShipOrder gfShipOrder) {
        try {
            String shipmentId = magentoOrderClient.shipOrder(orderId, gfShipOrder);//返回的结果是\"79\"，需要删除前后双引号
            return ResponseEntity.status(HttpStatus.OK).body(GfShipment.builder().entityId(Integer.parseInt(shipmentId)).build());
        } catch (OrderException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus(), "order"));
        }
    }

    /**
     * 根据 order id 来查询 tracks
     * 订单页面需要
     */
    @GetMapping("/{orderId}/tracks")
    public ResponseEntity<List<GfShipmentTrack>> getTracksByOrderId(@PathVariable Integer orderId) {
        try {
            GfShipmentResp gfShipmentResp = magentoShipmentClient.queryShipments("order_id=" + orderId, "items[tracks]");
            if (CollectionUtils.isEmpty(gfShipmentResp.getItems())) {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.EMPTY_LIST);
            }
            List<GfShipmentTrack> tracks = new ArrayList<>(gfShipmentResp.getItems().size() * 3);
            for (GfShipment item : gfShipmentResp.getItems()) {
                if (CollectionUtils.isNotEmpty(item.getTracks())) {
                    tracks.addAll(item.getTracks());
                }
            }
            if (tracks.size() > 1) {
                tracks.sort(Comparator.comparing(GfShipmentTrack::getCreatedAt));
            }
            return ResponseEntity.status(HttpStatus.OK).body(tracks);
        } catch (ShipmentException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus(), "shipment"));
        }
    }

    /**
     * 根据 customer id 来查询 我的订单
     * 订单页面需要
     */
    @GetMapping("{customerId}")
    public ResponseEntity<List<GfOrder>> getOrdersByCustomerId(@PathVariable Integer customerId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoOrderClient.queryOrders(customerId));
        } catch (OrderException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus(), "order"));
        }
    }

}