package com.gfashion.data.repository.dynamodb.interfaces;

import com.gfashion.data.repository.dynamodb.entity.GfOrderEntity;

public interface GfOrderRepository {

    GfOrderEntity addGfOrderEntity(GfOrderEntity GfOrderEntity);

    GfOrderEntity readGfOrderEntityById(String GfOrderEntityId);

    GfOrderEntity updateGfOrderEntity(GfOrderEntity GfOrderEntity);

    void deleteGfOrderEntity(String GfOrderEntityId);
}
