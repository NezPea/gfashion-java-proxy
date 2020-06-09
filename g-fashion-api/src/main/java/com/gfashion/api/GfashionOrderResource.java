package com.gfashion.api;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.GfShipmentTrack;
import com.gfashion.domain.sales.response.GfShipmentResp;
import com.gfashion.restclient.MagentoOrderClient;
import com.gfashion.restclient.MagentoShipmentClient;
import com.gfashion.restclient.magento.exception.*;
import com.gfashion.restclient.magento.sales.MagentoShipOrder;
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
@RequestMapping(path = "/gfashion/v1/order", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionOrderResource {
	private MagentoOrderClient magentoOrderClient;
	private MagentoShipmentClient magentoShipmentClient;

	@PostMapping(value = "/{orderId}/ship")
	public ResponseEntity<GfShipment> shipOrder(@PathVariable Integer orderId, @RequestBody MagentoShipOrder magentoShipOrder) {
		try {
			String shipmentId = magentoOrderClient.shipOrder(orderId, magentoShipOrder);//返回的结果是\"79\"，需要删除前后双引号
			shipmentId = shipmentId.substring(1, shipmentId.length() - 1);
			return ResponseEntity.status(HttpStatus.OK).body(GfShipment.builder().entity_id(Integer.parseInt(shipmentId)).build());
		} catch (UnauthorizedException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
		} catch (OrderNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
		} catch (OrderUnknowException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
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
				tracks.sort(Comparator.comparing(GfShipmentTrack::getCreated_at));
			}
			return ResponseEntity.status(HttpStatus.OK).body(tracks);
		} catch (UnauthorizedException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
		} catch (ShipmentNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
		} catch (ShipmentUnknowException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
		}
	}


}