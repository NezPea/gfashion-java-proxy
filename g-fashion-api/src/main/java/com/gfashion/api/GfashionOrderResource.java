package com.gfashion.api;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.restclient.MagentoShipmentClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {"application/json"})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionOrderResource {
	private MagentoShipmentClient magentoSalesClient;

	@PostMapping("/ship")
	public ResponseEntity<GfShipment> createShipment(@RequestBody @Validated GfShipment gfShipment) {
		try {
			gfShipment = magentoSalesClient.createShipment(gfShipment);
			return ResponseEntity.status(HttpStatus.CREATED).body(gfShipment);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}