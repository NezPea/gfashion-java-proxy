package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.cart.*;
import com.gfashion.domain.customer.GfCustomerExtensionAttributes;
import com.gfashion.domain.customer.GfCustomerRegion;
import com.gfashion.domain.homepage.GfCategory;
import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.domain.product.*;
import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;

import com.gfashion.domain.sales.GfShipment;
import com.gfashion.domain.sales.GfShipmentComment;
import com.gfashion.domain.sales.GfShipmentItem;
import com.gfashion.domain.sales.GfShipmentTrack;
import com.gfashion.domain.sales.response.GfShipmentResp;
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
import com.gfashion.restclient.magento.sales.MagentoShipment;
import com.gfashion.restclient.magento.sales.MagentoShipmentComment;
import com.gfashion.restclient.magento.sales.MagentoShipmentItem;
import com.gfashion.restclient.magento.sales.MagentoShipmentTrack;
import com.gfashion.restclient.magento.sales.response.MagentoShipmentResp;
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

    GfCart convertMagentoCartToGfCart(MagentoCart cart);
    GfCartItem convertMagentoCartItemToGfCartItem(MagentoCartItem cartItem);
    GfCartEstimateShippingMethod convertMagentoCartEstimateShippingMethodToGfCartEstimateShippingMethod(MagentoCartEstimateShippingMethod estimateShippingMethod);
    GfCartShippingInformation convertMagentoCartShippingInformationToGfCartShippingInformation(MagentoCartShippingInformation shippingInformation);

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

    //--shipment
//    @Mapping(source = "orderId",target = "order_id")
    MagentoShipment from(GfShipment shipment);

//    @Mapping(source = "orderItemId",target = "order_item_id")
    MagentoShipmentItem from(GfShipmentItem shipmentItem);

//    @Mapping(source = "orderId",target = "order_id")
    MagentoShipmentTrack from(GfShipmentTrack shipmentTrack);

//    @Mapping(source = "parentId",target = "parent_id")
    MagentoShipmentComment from(GfShipmentComment gfShipmentComment);
    //
//    @Mapping(target = "orderId",source = "order_id")
    GfShipment from(MagentoShipment shipment);

//    @Mapping(target = "orderItemId",source = "order_item_id")
    GfShipmentItem from(MagentoShipmentItem shipmentItem);

//    @Mapping(target = "orderId",source = "order_id")
    GfShipmentTrack from(MagentoShipmentTrack shipmentTrack);

//    @Mapping(target = "parentId",source = "parent_id")
    GfShipmentComment from(MagentoShipmentComment comment);

    GfShipmentResp from(MagentoShipmentResp magentoShipmentResp);
    //--shipment
}
