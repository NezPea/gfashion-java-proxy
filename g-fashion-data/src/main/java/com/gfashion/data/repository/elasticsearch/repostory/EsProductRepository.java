package com.gfashion.data.repository.elasticsearch.repostory;

import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, String> {
    Page<EsProduct> findByCategoryIdAndIdNot(Long categoryId, String id, Pageable pageable);

    Page<EsProduct> findByCategoryIdNot(Long categoryId, Pageable pageable);
}
