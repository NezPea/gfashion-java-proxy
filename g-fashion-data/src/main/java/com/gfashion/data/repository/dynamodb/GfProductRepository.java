package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfProductEntity;

public interface GfProductRepository {

    GfProductEntity createGfProductEntity(GfProductEntity GfProductEntity);

    GfProductEntity readGfProductEntityById(String GfProductEntityId);

    GfProductEntity updateGfProductEntity(GfProductEntity GfProductEntity);

    void deleteGfProductEntity(String GfProductEntityId);
}
