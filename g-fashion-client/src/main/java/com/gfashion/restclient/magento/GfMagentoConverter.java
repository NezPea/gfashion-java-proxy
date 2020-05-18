package com.gfashion.restclient.magento;

import com.gfashion.domain.product.GfProduct;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoConverter {
    GfProduct convertMagentoProductToGfProduct(MagentoProduct magentoProduct);
}
