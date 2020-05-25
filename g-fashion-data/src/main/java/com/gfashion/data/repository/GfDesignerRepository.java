package com.gfashion.data.repository;

import com.gfashion.data.entity.GfDesignerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Repository for the GfDesignerEntity.
 */
@Repository
public interface GfDesignerRepository extends JpaRepository<GfDesignerEntity, Integer> {
}