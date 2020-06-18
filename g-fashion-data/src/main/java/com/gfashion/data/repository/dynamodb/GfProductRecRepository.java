package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfProductRecEntity;

public interface GfProductRecRepository {

    GfProductRecEntity createGfProductRecEntity(GfProductRecEntity GfProductRecEntity);

    GfProductRecEntity readGfProductRecEntityById(String GfProductRecEntityId);

    GfProductRecEntity readGfProductRecEntityByProductId(String GfProductEntityId);

    GfProductRecEntity updateGfProductRecEntity(GfProductRecEntity GfProductRecEntity);

    void deleteGfProductRecEntity(String GfProductRecEntityId);
}
