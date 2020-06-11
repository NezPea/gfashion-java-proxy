package com.gfashion.api.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.gfashion.data.GfShipmentEntity;
import com.gfashion.data.repository.dynamodb.GfShipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/gfashion/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionShipmentDynamodbResource {

    private GfShipmentRepository gfShipmentRepository;

    @PostMapping(value = "/dynamodb/shipments", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfShipmentEntity> createShipment(@RequestBody GfShipmentEntity shipment) {
        try {
            GfShipmentEntity response = gfShipmentRepository.addGfShipmentEntity(shipment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/dynamodb/shipments/{shipmentId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfShipmentEntity> getShipment(@PathVariable String shipmentId) {
        try {
            GfShipmentEntity response = gfShipmentRepository.readGfShipmentEntityById(shipmentId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/dynamodb/shipments", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfShipmentEntity> updateShipment(@RequestBody GfShipmentEntity shipment) {
        try {
            if (gfShipmentRepository.readGfShipmentEntityById(shipment.getId()) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            GfShipmentEntity response = gfShipmentRepository.updateGfShipmentEntity(shipment);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/dynamodb/shipments/{shipmentId}", produces = "application/json;charset=utf-8")
    public ResponseEntity<GfShipmentEntity> deleteShipment(@PathVariable String shipmentId) {
        try {
            if (gfShipmentRepository.readGfShipmentEntityById(shipmentId) == null) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            gfShipmentRepository.deleteGfShipmentEntity(shipmentId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
