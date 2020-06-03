package com.gfashion.api;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerRegistration;
import com.gfashion.domain.sales.GfShipment;
import com.gfashion.restclient.MagentoSalesClient;
import com.gfashion.restclient.magento.exception.CustomerCreationException;
import com.gfashion.restclient.magento.exception.CustomerNotFoundException;
import com.gfashion.restclient.magento.exception.CustomerUnknowException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1/sales", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionSalesResource {
	private MagentoSalesClient magentoSalesClient;

	@PostMapping("/createShipment")
	public ResponseEntity<GfCustomer> creatCustomer(@RequestBody GfCustomerRegistration gfCustomer) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(magentoSalesClient.createCustomer(gfCustomer));
		} catch (CustomerCreationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getErrorMessage());
		} catch (CustomerUnknowException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
		}
	}

	@GetMapping("/shipment/{shipmentId}")
	public ResponseEntity<GfShipment> getShipmentById(@PathVariable Integer shipmentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(magentoSalesClient.getShipmentById(shipmentId));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PutMapping("/editShipment/{customerId}")
	public ResponseEntity<GfCustomer> getShipmentById(@RequestBody GfCustomerRegistration gfCustomer, @PathVariable Integer customerId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(magentoSalesClient.updateCustomerById(gfCustomer, customerId));
		} catch (CustomerNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getErrorMessage());
		} catch (CustomerUnknowException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
		}

	}
}