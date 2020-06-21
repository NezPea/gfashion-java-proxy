package com.gfashion.data.repository.dynamodb.Interface;

import com.gfashion.data.repository.dynamodb.entity.GfSkuEntity;

import java.util.List;

public interface GfSkuRepository {

    GfSkuEntity createGfSkuEntity(GfSkuEntity GfSkuEntity);

    GfSkuEntity readGfSkuEntityById(String productId,String skuId);

    GfSkuEntity updateGfSkuEntity(GfSkuEntity GfSkuEntity);

    void deleteGfSkuEntity(String productId,String skuId);
    List<GfSkuEntity> scanGfSkuEntityByColor(String color);

    List<GfSkuEntity> loadGfSkuEntity(List<GfSkuEntity> GfSkuEntityList);

}
