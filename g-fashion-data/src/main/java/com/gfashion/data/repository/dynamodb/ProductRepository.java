package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfProductEntity;

public interface ProductRepository {

    GfProductEntity createGfProductEntity(GfProductEntity GfProductEntity);

    GfProductEntity readGfProductEntityById(String GfProductEntityId);

    GfProductEntity updateGfProductEntity(GfProductEntity GfProductEntity);

    void deleteGfProductEntity(String GfProductEntityId);
}
