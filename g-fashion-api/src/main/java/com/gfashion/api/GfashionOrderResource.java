package com.gfashion.api;

import com.gfashion.restclient.MagentoOrderClient;
import com.gfashion.restclient.MagentoShipmentClient;
import com.gfashion.restclient.magento.sales.MagentoShipOrder;
import com.gfashion.restclient.magento.sales.MagentoShipment;
import com.gfashion.restclient.magento.sales.MagentoShipmentTrack;
import com.gfashion.restclient.magento.sales.response.MagentoShipmentResp;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "/gfashion/v1/order", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionOrderResource {
	private MagentoOrderClient magentoOrderClient;
	private MagentoShipmentClient magentoShipmentClient;

	@PostMapping(value = "/{orderId}/ship", produces = {"text/plain"})
	public ResponseEntity<String> createShipment(@PathVariable Integer orderId, @RequestBody MagentoShipOrder magentoShipOrder) {
		try {
			String shipmentId = magentoOrderClient.createShipment(orderId, magentoShipOrder);
			return ResponseEntity.status(HttpStatus.OK).body(shipmentId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * 根据 orderid 来查询 tracks
	 * 订单页面需要
	 */
	@GetMapping("/{orderId}/tracks")
	public ResponseEntity<List<MagentoShipmentTrack>> getTracksByOrderId(@PathVariable Integer orderId) {
		try {
			MagentoShipmentResp magentoShipmentResp = magentoShipmentClient.queryShipments("order_id=" + orderId, "items[tracks]");
			List<MagentoShipmentTrack> tracks = new ArrayList<>(magentoShipmentResp.getItems().size() * 3);
			for (MagentoShipment item : magentoShipmentResp.getItems()) {
				if (CollectionUtils.isNotEmpty(item.getTracks())) {
					tracks.addAll(item.getTracks());
				}
			}
			tracks.sort(Comparator.comparing(MagentoShipmentTrack::getCreated_at));
			return ResponseEntity.status(HttpStatus.OK).body(tracks);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}


}