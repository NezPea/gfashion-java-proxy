package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.cart.*;
import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;
import com.gfashion.domain.customer.GfCustomerExtensionAttributes;
import com.gfashion.domain.customer.GfCustomerRegion;
import com.gfashion.domain.homepage.GfCategory;
import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.domain.product.*;
import com.gfashion.domain.store.GfStore;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.domain.store.GfStoreGroup;
import com.gfashion.domain.store.GfWebsite;
import com.gfashion.restclient.magento.MagentoStore;
import com.gfashion.restclient.magento.MagentoStoreConfig;
import com.gfashion.restclient.magento.MagentoStoreGroup;
import com.gfashion.restclient.magento.MagentoWebsite;
import com.gfashion.restclient.magento.cart.*;
import com.gfashion.restclient.magento.customer.MagentoCustomer;
import com.gfashion.restclient.magento.customer.MagentoCustomerAddress;
import com.gfashion.restclient.magento.customer.MagentoCustomerExtensionAttributes;
import com.gfashion.restclient.magento.customer.MagentoCustomerRegion;
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

    GfCart convertMagentoCartToGfCart(MagentoCart cart);

    GfCartItem convertMagentoCartItemToGfCartItem(MagentoCartItem cartItem);

    GfCartEstimateShippingMethod convertMagentoCartEstimateShippingMethodToGfCartEstimateShippingMethod(MagentoCartEstimateShippingMethod estimateShippingMethod);

    GfCartPaymentMethod convertMagentoCartPaymentMethodToGfCartPaymentMethod(MagentoCartPaymentMethod paymentMethod);

    GfCartShippingInformation convertMagentoCartShippingInformationToGfCartShippingInformation(MagentoCartShippingInformation shippingInformation);

    MagentoCartItem convertGfCartItemToMagentoCartItem(GfCartItem cartItem);

    MagentoCartAddress convertGfCartAddressToMagentoCartAddress(GfCartAddress cartAddress);

    MagentoCartAddressInformation convertGfCartAddressInformationToMagentoCartAddressInformation(GfCartAddressInformation cartAddress);

    MagentoCartPaymentInformation convertGfCartPaymentInformationToMagentoCartPaymentInformation(GfCartPaymentInformation cartAddress);

    public List<HomepageBrand> convertMagentoCategoriesToHomeBrands(List<MagentoCategory> magentoCategories);

    @Mapping(source = "custom_attributes", target = "link", qualifiedByName = "getLinkFromAttribute")
    @Mapping(source = "custom_attributes", target = "photoUrl", qualifiedByName = "getPhotoUrlFromAttribute")
    HomepageBrand convertMagentoCategoryToHomeBrand(MagentoCategory magentoCategory);

    List<HomepageDesigner> convertMagentoCategoriesToHomeDesigners(List<MagentoCategory> magentoCategories);

    @Mapping(source = "custom_attributes", target = "photoUrl", qualifiedByName = "getPhotoUrlFromAttribute")
    HomepageDesigner convertMagentoCategoryToHomeDesigner(MagentoCategory magentoCategory);

    @Named("getPhotoUrlFromAttribute")
    static String getPhotoUrlFromAttribute(List<Map<String, String>> customerAttributes) {
        return getCustomerAttributeValue(customerAttributes, "url_path");
    }

    static String getCustomerAttributeValue(List<Map<String, String>> customerAttributes, String attributeName) {
        if (customerAttributes == null) {
            return null;
        }
        List<String> res = customerAttributes.stream().
                filter(attribute -> attribute.get("attribute_code").equalsIgnoreCase(attributeName)).
                map(p -> p.get("value")).collect(Collectors.toList());
        return res != null && res.size() != 0 ? res.get(0) : null;
    }

    //--shipment
//    MagentoShipment from(GfShipment shipment);
//
//    MagentoShipmentItem from(GfShipmentItem shipmentItem);
//
//    MagentoShipmentTrack from(GfShipmentTrack shipmentTrack);
//
//    MagentoShipmentComment from(GfShipmentComment gfShipmentComment);
//
//    MagentoShipOrder from(GfShipOrder gfShipOrder);
//
//    MagentoShipmentItemCreation from(GfShipmentItemCreation gfShipmentItemCreation);
//
//    MagentoShipmentCommentCreation from(GfShipmentCommentCreation gfShipmentCommentCreation);
//
//    MagentoShipmentTrackCreation from(GfShipmentTrackCreation gfShipmentTrackCreation);
//
//    MagentoShipmentPackageCreation from(GfShipmentPackageCreation gfShipmentPackageCreation);


    //
//    GfShipment from(MagentoShipment shipment);
//
//    GfShipmentItem from(MagentoShipmentItem shipmentItem);
//
//    GfShipmentTrack from(MagentoShipmentTrack shipmentTrack);
//
//    GfShipmentComment from(MagentoShipmentComment comment);
//
//    GfShipmentResp from(MagentoShipmentResp magentoShipmentResp);
    //--shipment
}
