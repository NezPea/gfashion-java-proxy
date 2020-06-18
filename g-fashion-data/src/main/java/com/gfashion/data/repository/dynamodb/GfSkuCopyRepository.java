package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfSkuCopyEntity;

import java.util.Map;

public interface GfSkuCopyRepository {

    GfSkuCopyEntity createGfSkuCopyEntity(GfSkuCopyEntity GfSkuCopyEntity);

    GfSkuCopyEntity readGfSkuCopyEntityById(String productId,String skuId);

    Map<String, GfSkuCopyEntity> readGfSkuCopyEntityByProductId(String GfProductEntityId);

    GfSkuCopyEntity updateGfSkuCopyEntity(GfSkuCopyEntity GfSkuCopyEntity);

    void deleteGfSkuCopyEntity(String productId,String skuId);
}
