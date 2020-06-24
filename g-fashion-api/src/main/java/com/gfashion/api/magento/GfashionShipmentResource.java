package com.gfashion.api.magento;

import com.gfashion.api.utility.ExceptionStringFactory;
import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.response.GfShipmentResp;
import com.gfashion.magento.client.MagentoShipmentClient;
import com.gfashion.magento.exception.ShipmentException;
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

    private ExceptionStringFactory exceptionStringFactory;

    @PostMapping("/shipment")
    public ResponseEntity<GfShipment> updateShipment(@RequestBody @Validated GfShipment gfShipment) {
        try {
            gfShipment = magentoShipmentClient.updateShipment(gfShipment);
            return ResponseEntity.status(HttpStatus.OK).body(gfShipment);
        } catch (ShipmentException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus(), "shipment"));
        }
    }

    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<GfShipment> getShipmentById(@PathVariable Integer shipmentId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoShipmentClient.getShipmentById(shipmentId));
        } catch (ShipmentException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus(), "shipment"));
        }
    }

    @GetMapping("/shipments")
    public ResponseEntity<GfShipmentResp> queryShipments(String searchCriteria, String fields) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoShipmentClient.queryShipments(searchCriteria, fields));
        } catch (ShipmentException e) {
            throw new ResponseStatusException(e.getStatus(),
                    exceptionStringFactory.getExceptionStringForStatusCode(e.getStatus(), "shipment"));
        }
    }
}