package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfDesignerEntity;
import com.gfashion.domain.designer.GfDesignerSearchAttributeValueMappings;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data Repository for the GfDesignerEntity.
 */
@Repository
public interface GfDesignerRepository {
    GfDesignerEntity createGfDesignerEntity(GfDesignerEntity gfDesignerEntity);

    GfDesignerEntity readGfDesignerEntity(String gfDesignerEntityId);

    GfDesignerEntity updateGfDesignerEntity(GfDesignerEntity gfDesignerEntity);

    void deleteGfDesignerEntity(String gfDesignerEntityId);

    public List<GfDesignerEntity> searchGfDesignerEntities(GfDesignerSearchAttributeValueMappings attributeValueMappings);
}
