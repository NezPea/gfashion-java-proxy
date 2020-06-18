package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfProductRecEntity;
import com.gfashion.data.GfSkuEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GfSkuRepository {

    GfSkuEntity createGfSkuEntity(GfSkuEntity GfSkuEntity);

    GfSkuEntity readGfSkuEntityById(String GfSkuEntityId);

    GfSkuEntity updateGfSkuEntity(GfSkuEntity GfSkuEntity);

    void deleteGfSkuEntity(String GfSkuEntityId);
}
