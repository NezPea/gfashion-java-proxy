package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfShipmentEntity;

public interface GfShipmentRepository {

    GfShipmentEntity createGfShipmentEntity(GfShipmentEntity GfShipmentEntity);

    GfShipmentEntity readGfShipmentEntityById(String GfShipmentEntityId);

    GfShipmentEntity updateGfShipmentEntity(GfShipmentEntity GfShipmentEntity);

    void deleteGfShipmentEntity(String GfShipmentEntityId);
}
