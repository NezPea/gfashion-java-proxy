package com.gfashion.data.repository.dynamodb.interfaces;

import com.gfashion.data.repository.dynamodb.entity.GfShipmentEntity;

public interface GfShipmentRepository {

    GfShipmentEntity addGfShipmentEntity(GfShipmentEntity GfShipmentEntity);

    GfShipmentEntity readGfShipmentEntityById(String GfShipmentEntityId);

    GfShipmentEntity updateGfShipmentEntity(GfShipmentEntity GfShipmentEntity);

    void deleteGfShipmentEntity(String GfShipmentEntityId);
}
