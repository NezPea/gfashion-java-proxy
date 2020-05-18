package com.gfashion.restclient.magento;

import com.gfashion.domain.product.*;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoConverter {
    GfProduct convertMagentoProductToGfProduct(MagentoProduct magentoProduct);
    GfProductLinks convertMagentoProductLinksToGfProductLinks(MagentoProductLinks magentoProductLinks);
    GfProductMediaGalleryEntries convertMagentoProductMediaGalleryEntriesToGfProductMediaGalleryEntries(MagentoProductMediaGalleryEntries magentoProductMediaGalleryEntries);
    GfProductCustomAttributes convertMagentoProductCustomAttributesToGfProductCustomAttributes(MagentoProductCustomAttributes magentoProductCustomAttributes);
}
