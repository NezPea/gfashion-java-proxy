package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfCategoryEntity;
public interface GfCategoryRepository {
        GfCategoryEntity createGfCategoryEntity(GfCategoryEntity GfCategoryEntity);

        GfCategoryEntity readGfCategoryEntityById(String GfCategoryEntityId);

        GfCategoryEntity updateGfCategoryEntity(GfCategoryEntity GfCategoryEntity);

        void deleteGfCategoryEntity(String GfCategoryEntityId);
}
