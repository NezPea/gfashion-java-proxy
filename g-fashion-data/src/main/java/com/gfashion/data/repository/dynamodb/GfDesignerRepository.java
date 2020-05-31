package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfDesignerEntity;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Repository for the GfDesignerEntity.
 */
@Repository
public interface GfDesignerRepository {
    GfDesignerEntity createGfDesignerEntity(GfDesignerEntity gfDesignerEntity);

    GfDesignerEntity readGfDesignerEntity(String gfDesignerEntityId);

    GfDesignerEntity updateGfDesignerEntity(GfDesignerEntity gfDesignerEntity);

    void deleteGfDesignerEntity(String gfDesignerEntityId);
}
