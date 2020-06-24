package com.gfashion.data.repository.elasticsearch.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfashion.data.repository.elasticsearch.model.EsCategory;
import com.gfashion.data.repository.elasticsearch.model.EsProduct;
import com.gfashion.domain.elasticsearch.GfCategory;
import com.gfashion.domain.elasticsearch.GfProduct;
import com.gfashion.domain.elasticsearch.MagentoCategory;
import org.mapstruct.Mapper;

@Mapper
public interface ElasticsearchMapper {
    ObjectMapper MAPPER = new ObjectMapper();

    GfProduct convertProduct(EsProduct product);

    MagentoCategory convertCategoryFromEsToMagento(EsCategory category);
    GfCategory convertCategoryFromMagentoToGf(MagentoCategory category);
    EsCategory convertCategoryFromMagentoToEs(MagentoCategory category);

}
