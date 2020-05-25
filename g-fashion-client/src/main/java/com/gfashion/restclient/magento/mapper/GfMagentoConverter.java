package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.domain.product.*;
import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;

import com.gfashion.domain.store.GfStore;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.domain.store.GfStoreGroup;
import com.gfashion.domain.store.GfWebsite;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.restclient.magento.product.MagentoEvaAttribute;
import com.gfashion.restclient.magento.customer.MagentoCustomer;
import com.gfashion.restclient.magento.customer.MagentoCustomerAddress;
import com.gfashion.restclient.magento.*;

import com.gfashion.restclient.magento.homepage.MagentoCategory;
import com.gfashion.restclient.magento.product.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface GfMagentoConverter {
    GfCustomer convertMagentoCustomerToGfCustomer(MagentoCustomer magentoCustomer);
    GfProduct convertMagentoProductToGfProduct(MagentoProduct magentoProduct);
    GfProductCategory convertMagentoProductCategoryToGfProductCategory(MagentoProductCategory magentoProductCategory);
    GfEvaAttribute convertMagentoEvaAttributeToGfEvaAttribute(MagentoEvaAttribute magentoEvaAttribute);
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

    public List<HomepageBrand> convertMagentoCategoriesToHomeBrands(List<MagentoCategory> magentoCategories);
    @Mapping(source = "custom_attributes", target = "link", qualifiedByName = "getLinkFromAttribute")
    @Mapping(source = "custom_attributes", target = "photoUrl", qualifiedByName = "getPhotoUrlFromAttribute")
    public HomepageBrand convertMagentoCategoryToHomeBrand(MagentoCategory magentoCategory);

    public List<HomepageDesigner> convertMagentoCategoriesToHomeDesigners(List<MagentoCategory> magentoCategories);
    @Mapping(source = "custom_attributes", target = "photoUrl", qualifiedByName = "getPhotoUrlFromAttribute")
    public HomepageDesigner convertMagentoCategoryToHomeDesigner(MagentoCategory magentoCategory);

    @Named("getPhotoUrlFromAttribute")
    public static String getPhotoUrlFromAttribute(List<Map<String, String>> customerAttributes){
        return getCustomerAttributeValue(customerAttributes, "url_path");
    }

    public static String getCustomerAttributeValue(List<Map<String, String>> customerAttributes, String attributeName){
        if (customerAttributes == null){
            return null;
        }
        List<String> res = customerAttributes.stream().
                filter(attribute -> attribute.get("attribute_code").equalsIgnoreCase(attributeName)).
                map(p -> p.get("value")).collect(Collectors.toList());
        return res != null && res.size() != 0 ? res.get(0) : null;
    }
}
