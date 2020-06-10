package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfOrderEntity;

public interface GfOrderRepository {

    GfOrderEntity createGfOrderEntity(GfOrderEntity GfOrderEntity);

    GfOrderEntity readGfOrderEntityById(String GfOrderEntityId);

    GfOrderEntity updateGfOrderEntity(GfOrderEntity GfOrderEntity);

    void deleteGfOrderEntity(String GfOrderEntityId);
}
