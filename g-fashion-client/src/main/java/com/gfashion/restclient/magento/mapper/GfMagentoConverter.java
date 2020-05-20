package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.product.*;
import com.gfashion.restclient.magento.MagentoProduct;
import com.gfashion.restclient.magento.MagentoProductCustomAttributes;
import com.gfashion.restclient.magento.MagentoProductLinks;
import com.gfashion.restclient.magento.MagentoProductMediaGalleryEntries;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoConverter {
    GfProduct convertMagentoProductToGfProduct(MagentoProduct magentoProduct);
    GfProductLinks convertMagentoProductLinksToGfProductLinks(MagentoProductLinks magentoProductLinks);
    GfProductMediaGalleryEntries convertMagentoProductMediaGalleryEntriesToGfProductMediaGalleryEntries(MagentoProductMediaGalleryEntries magentoProductMediaGalleryEntries);
    GfProductCustomAttributes convertMagentoProductCustomAttributesToGfProductCustomAttributes(MagentoProductCustomAttributes magentoProductCustomAttributes);
}
