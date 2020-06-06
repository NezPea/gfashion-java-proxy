package com.gfashion.api;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.restclient.MagentoShipmentClient;
import com.gfashion.restclient.magento.sales.response.MagentoShipmentResp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1/shipment", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionShipmentResource {
	private MagentoShipmentClient magentoShipmentClient;

	@PostMapping("/")
	public ResponseEntity<GfShipment> updateShipment(@RequestBody @Validated GfShipment gfShipment) {
		try {
			gfShipment = magentoShipmentClient.createShipment(gfShipment);
			return ResponseEntity.status(HttpStatus.CREATED).body(gfShipment);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/{shipmentId}")
	public ResponseEntity<GfShipment> getShipmentById(@PathVariable Integer shipmentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(magentoShipmentClient.getShipmentById(shipmentId));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}


	@GetMapping("/shipments")
	public ResponseEntity<MagentoShipmentResp> queryShipments(String searchCriteria, String fields) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(magentoShipmentClient.queryShipments(searchCriteria, fields));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}