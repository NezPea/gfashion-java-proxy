package com.gfashion.api;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.response.GfShipmentResp;
import com.gfashion.restclient.MagentoShipmentClient;
import com.gfashion.restclient.magento.exception.ShipmentNotFoundException;
import com.gfashion.restclient.magento.exception.ShipmentUnknowException;
import com.gfashion.restclient.magento.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionShipmentResource {
	private MagentoShipmentClient magentoShipmentClient;

	@PostMapping("/shipment")
	public ResponseEntity<GfShipment> updateShipment(@RequestBody @Validated GfShipment gfShipment) {
		try {
			gfShipment = magentoShipmentClient.updateShipment(gfShipment);
			return ResponseEntity.status(HttpStatus.OK).body(gfShipment);
		} catch (UnauthorizedException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
		} catch (ShipmentNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
		} catch (ShipmentUnknowException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
		}
	}

	@GetMapping("/shipment/{shipmentId}")
	public ResponseEntity<GfShipment> getShipmentById(@PathVariable Integer shipmentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(magentoShipmentClient.getShipmentById(shipmentId));
		} catch (UnauthorizedException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
		} catch (ShipmentNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
		} catch (ShipmentUnknowException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
		}
	}

	@GetMapping("/shipments")
	public ResponseEntity<GfShipmentResp> queryShipments(String searchCriteria, String fields) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(magentoShipmentClient.queryShipments(searchCriteria, fields));
		} catch (UnauthorizedException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getErrorMessage());
		} catch (ShipmentNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
		} catch (ShipmentUnknowException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
		}
	}

}