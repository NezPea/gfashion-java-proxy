package com.gfashion.data.repository.dynamodb.Interface;

import com.gfashion.data.repository.dynamodb.entity.GfProductEntity;

public interface GfProductRepository {

    GfProductEntity createGfProductEntity(GfProductEntity GfProductEntity);

    GfProductEntity readGfProductEntityById(String GfProductEntityId);

    GfProductEntity updateGfProductEntity(GfProductEntity GfProductEntity);

    void deleteGfProductEntity(String GfProductEntityId);
}
