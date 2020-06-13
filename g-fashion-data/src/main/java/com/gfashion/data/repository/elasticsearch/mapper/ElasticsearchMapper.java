package com.gfashion.data.repository.elasticsearch.mapper;

import com.gfashion.data.repository.elasticsearch.model.EsCategory;
import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.domain.elasticsearch.GfCategory;
import com.gfashion.domain.elasticsearch.GfProduct;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ElasticsearchMapper {
    GfProduct convertProduct(EsProduct product);
    GfCategory convertCategory(EsCategory category);
    List<GfProduct> convertProducts(Collection<EsProduct> products);
}
