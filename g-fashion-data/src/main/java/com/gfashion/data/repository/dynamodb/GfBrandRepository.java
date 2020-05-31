package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfBrandEntity;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Repository for the GfBrandEntity.
 */
@Repository
public interface GfBrandRepository{
    GfBrandEntity createGfBrandEntity(GfBrandEntity GfBrandEntity);

    GfBrandEntity readGfBrandEntity(String GfBrandEntityId);

    GfBrandEntity updateGfBrandEntity(GfBrandEntity GfBrandEntity);

    void deleteGfBrandEntity(String GfBrandEntityId);
}
