package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.entity.GfProductEntity;

public interface ProductRepository {
    GfProductEntity createGfProductEntity(GfProductEntity GfProductEntity);

    GfProductEntity readGfProductEntity(String GfProductEntityId);

    GfProductEntity updateGfProductEntity(GfProductEntity GfProductEntity);

    void deleteGfProductEntity(String GfProductEntityId);
}
