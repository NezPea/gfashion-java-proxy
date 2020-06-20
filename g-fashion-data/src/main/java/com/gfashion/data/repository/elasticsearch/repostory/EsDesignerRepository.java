package com.gfashion.data.repository.elasticsearch.repostory;

import com.gfashion.data.repository.elasticsearch.model.EsDesigner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsDesignerRepository extends ElasticsearchRepository<EsDesigner, String> {
    Page<EsDesigner> findByDesignerId(String id, Pageable pageable);
}
