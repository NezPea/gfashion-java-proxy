package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.product.*;
import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;

import com.gfashion.domain.store.GfStore;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.domain.store.GfStoreGroup;
import com.gfashion.domain.store.GfWebsite;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.restclient.magento.customer.MagentoCustomer;
import com.gfashion.restclient.magento.customer.MagentoCustomerAddress;
import com.gfashion.restclient.magento.*;

import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoConverter {
    GfCustomer convertMagentoCustomerToGfCustomer(MagentoCustomer magentoCustomer);
    GfProduct convertMagentoProductToGfProduct(MagentoProduct magentoProduct);
    GfProductLink convertMagentoProductLinkToGfProductLink(MagentoProductLink magentoProductLink);
    GfProductCustomAttribute convertMagentoProductCustomAttributeToGfProductCustomAttribute(MagentoProductCustomAttribute magentoProductCustomAttribute);
    GfExtensionAttribute convertMagentoExtensionAttributeToGfExtensionAttribute(MagentoExtensionAttribute magentoExtensionAttribute);
    GfMediaGalleryEntry convertMagentoMediaGalleryEntryToGfMediaGalleryEntry(MagentoMediaGalleryEntry magentoMediaGalleryEntry);
    GfProductSearchResponse convertMagentoProductSearchToGfProductSearch(MagentoProductSearchResponse magentoProductSearchResponse);
    MagentoCustomer convertGfCustomerToMagentoCustomer(GfCustomer gfCustomer);
    GfCustomerAddress convertMagentoCustomerAddressToGfCustomerAddress(MagentoCustomerAddress magentoCustomerAddress);
    MagentoCustomerAddress convertGfCustomerAddressToMagentoCustomerAddress(GfCustomerAddress gfCustomerAddress);
    GfStore[] convertMagentoStoreToGfStoreArray(MagentoStore[] magentoStore);
    GfWebsite[] convertMagentoWebsiteToGfWebsiteArray(MagentoWebsite[] magentoWebsites);
    GfStoreGroup[] convertMagentoStoreGroupToGfStoreGroupArray(MagentoStoreGroup[] magentoStoreGroups);
    GfStoreConfig[] convertMagentoStoreConfigToGfStoreConfigArray(MagentoStoreConfig[] magentoStoreConfigs);
}
