package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfSkuEntity;
import com.gfashion.data.GfSkuEntity;

import java.util.List;
import java.util.Map;

public interface GfSkuRepository {

    GfSkuEntity createGfSkuEntity(GfSkuEntity GfSkuEntity);

    GfSkuEntity readGfSkuEntityById(String productId,String skuId);

    GfSkuEntity updateGfSkuEntity(GfSkuEntity GfSkuEntity);

    void deleteGfSkuEntity(String productId,String skuId);
    List<GfSkuEntity> scanGfSkuEntityByColor(String color);

    List<GfSkuEntity> loadGfSkuEntity(List<GfSkuEntity> GfSkuEntityList);

}
