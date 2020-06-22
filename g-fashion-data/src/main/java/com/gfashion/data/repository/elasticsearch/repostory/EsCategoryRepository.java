package com.gfashion.data.repository.elasticsearch.repostory;

import com.gfashion.data.repository.elasticsearch.model.EsCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EsCategoryRepository extends ElasticsearchRepository<EsCategory, Long> {
    Set<EsCategory> findByLevel(Integer level);
}
