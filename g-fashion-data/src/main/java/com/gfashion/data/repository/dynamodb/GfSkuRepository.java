package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfSkuEntity;

public interface GfSkuRepository {

    GfSkuEntity createGfSkuEntity(GfSkuEntity GfSkuEntity);

    GfSkuEntity readGfSkuEntityById(String GfSkuEntityId);

    GfSkuEntity updateGfSkuEntity(GfSkuEntity GfSkuEntity);

    void deleteGfSkuEntity(String GfSkuEntityId);
}
