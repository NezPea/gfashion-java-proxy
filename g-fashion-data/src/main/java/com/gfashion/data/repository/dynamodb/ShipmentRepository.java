package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfShipmentEntity;

public interface ShipmentRepository {

    GfShipmentEntity get(String incrementId);

}
