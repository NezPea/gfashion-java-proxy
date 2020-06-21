package com.gfashion.data.repository.dynamodb.Interface;

import com.gfashion.data.repository.dynamodb.entity.GfProductRecEntity;

public interface GfProductRecRepository {

    GfProductRecEntity createGfProductRecEntity(GfProductRecEntity GfProductRecEntity);

    GfProductRecEntity readGfProductRecEntityById(String GfProductRecEntityId);

    GfProductRecEntity updateGfProductRecEntity(GfProductRecEntity GfProductRecEntity);

    void deleteGfProductRecEntity(String GfProductRecEntityId);
}
