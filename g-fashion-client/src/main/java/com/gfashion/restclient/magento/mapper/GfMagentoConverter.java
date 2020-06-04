package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.cart.GFCartItem;
import com.gfashion.domain.cart.GfCart;
import com.gfashion.domain.customer.GfCustomerExtensionAttributes;
import com.gfashion.domain.customer.GfCustomerRegion;
import com.gfashion.domain.homepage.GfCategory;
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
import com.gfashion.restclient.magento.customer.*;
import com.gfashion.restclient.magento.product.MagentoEvaAttribute;
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
    GfStockItem convertMagentoStockItemToGfStockItem(MagentoStockItem magentoStockItem);
    GfConfigurableProductOption convertMagentoConfigurableProductOptionToGfConfigurableProductOption(MagentoConfigurableProductOption magentoConfigurableProductOption);
    GfConfigurableProductOptionValue convertMagentoConfigurableProductOptionValueToGfConfigurableProductOptionValue(MagentoConfigurableProductOptionValue magentoConfigurableProductOptionValue);
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
    GfCustomerExtensionAttributes convertMagentoCustomerExtensionAttributesToGfCustomerExtensionAttributes(MagentoCustomerExtensionAttributes magentoCustomerExtensionAttributes);
    MagentoCustomerExtensionAttributes convertGfCustomerExtensionAttributesToMagentoCustomerExtensionAttributes(GfCustomerExtensionAttributes gfCustomerExtensionAttributes);
    GfCustomerRegion convertMagentoCustomerRegionToGfCustomerRegion(MagentoCustomerRegion magentoCustomerRegion);
    MagentoCustomerRegion convertGfCustomerRegionToMagentoCustomerRegion(GfCustomerRegion gfCustomerRegion);
    GfStore[] convertMagentoStoreToGfStoreArray(MagentoStore[] magentoStore);
    GfWebsite[] convertMagentoWebsiteToGfWebsiteArray(MagentoWebsite[] magentoWebsites);
    GfStoreGroup[] convertMagentoStoreGroupToGfStoreGroupArray(MagentoStoreGroup[] magentoStoreGroups);
    GfStoreConfig[] convertMagentoStoreConfigToGfStoreConfigArray(MagentoStoreConfig[] magentoStoreConfigs);
    List<GfCategory> convertMagentoCategoriesToGfCategories(List<MagentoCategory> magentoCategories);
    GfCategory convertMagentoCategoryToGfCategory(MagentoCategory magentoCategory);

    GfCart convertMagentoCartToGfCart(MagentoCart magentoCart);
    GFCartItem convertMagentoCartItemToGfCartItem(MagentoCartItem magentoCart);

    public List<HomepageBrand> convertMagentoCategoriesToHomeBrands(List<MagentoCategory> magentoCategories);
    @Mapping(source = "custom_attributes", target = "link", qualifiedByName = "getLinkFromAttribute")
    @Mapping(source = "custom_attributes", target = "photoUrl", qualifiedByName = "getPhotoUrlFromAttribute")
    HomepageBrand convertMagentoCategoryToHomeBrand(MagentoCategory magentoCategory);

    List<HomepageDesigner> convertMagentoCategoriesToHomeDesigners(List<MagentoCategory> magentoCategories);
    @Mapping(source = "custom_attributes", target = "photoUrl", qualifiedByName = "getPhotoUrlFromAttribute")
    HomepageDesigner convertMagentoCategoryToHomeDesigner(MagentoCategory magentoCategory);

    @Named("getPhotoUrlFromAttribute")
    static String getPhotoUrlFromAttribute(List<Map<String, String>> customerAttributes){
        return getCustomerAttributeValue(customerAttributes, "url_path");
    }

    static String getCustomerAttributeValue(List<Map<String, String>> customerAttributes, String attributeName){
        if (customerAttributes == null){
            return null;
        }
        List<String> res = customerAttributes.stream().
                filter(attribute -> attribute.get("attribute_code").equalsIgnoreCase(attributeName)).
                map(p -> p.get("value")).collect(Collectors.toList());
        return res != null && res.size() != 0 ? res.get(0) : null;
    }
}
