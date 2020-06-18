package com.gfashion.data.repository.elasticsearch.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfashion.data.repository.elasticsearch.model.EsCategory;
import com.gfashion.data.repository.elasticsearch.model.EsDesigner;
import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.domain.elasticsearch.GfCategory;
import com.gfashion.domain.elasticsearch.GfDesigner;
import com.gfashion.domain.elasticsearch.GfProduct;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ElasticsearchMapper {
    ObjectMapper MAPPER = new ObjectMapper();

    GfProduct convertProduct(EsProduct product);
    GfCategory convertCategory(EsCategory category);
    GfDesigner convertDesigner(EsDesigner designer);
    List<GfProduct> convertProducts(Collection<EsProduct> products);
}
