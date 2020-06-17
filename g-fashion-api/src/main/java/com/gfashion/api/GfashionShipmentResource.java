package com.gfashion.api;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.response.GfShipmentResp;
import com.gfashion.restclient.MagentoShipmentClient;
import com.gfashion.restclient.magento.exception.ShipmentException;
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
        } catch (ShipmentException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<GfShipment> getShipmentById(@PathVariable Integer shipmentId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoShipmentClient.getShipmentById(shipmentId));
        } catch (ShipmentException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

    @GetMapping("/shipments")
    public ResponseEntity<GfShipmentResp> queryShipments(String searchCriteria, String fields) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(magentoShipmentClient.queryShipments(searchCriteria, fields));
        } catch (ShipmentException e) {
            throw new ResponseStatusException(e.getStatus(), e.getErrorMessage());
        }
    }

}