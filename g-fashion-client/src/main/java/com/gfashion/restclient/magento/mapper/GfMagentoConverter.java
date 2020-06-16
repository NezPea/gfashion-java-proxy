package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.cart.*;
import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;
import com.gfashion.domain.customer.GfCustomerExtensionAttributes;
import com.gfashion.domain.customer.GfCustomerRegion;
import com.gfashion.domain.order.GfOrderItem;
import com.gfashion.domain.order.GfOrderItemOrderId;
import com.gfashion.domain.order.GfOrderResp;
import com.gfashion.domain.product.*;
import com.gfashion.domain.sales.*;
import com.gfashion.domain.sales.response.GfShipmentResp;
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
import com.gfashion.restclient.magento.order.MagentoOrderItem;
import com.gfashion.restclient.magento.order.MagentoOrderItemOrderId;
import com.gfashion.restclient.magento.order.MagentoOrderResp;
import com.gfashion.restclient.magento.product.*;
import com.gfashion.restclient.magento.sales.*;
import com.gfashion.restclient.magento.sales.response.MagentoShipmentResp;
import org.mapstruct.Mapper;

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

    GfCart convertMagentoCartToGfCart(MagentoCart cart);

    GfCartItem convertMagentoCartItemToGfCartItem(MagentoCartItem cartItem);

    GfCartEstimateShippingMethod convertMagentoCartEstimateShippingMethodToGfCartEstimateShippingMethod(MagentoCartEstimateShippingMethod estimateShippingMethod);

    GfCartPaymentMethod convertMagentoCartPaymentMethodToGfCartPaymentMethod(MagentoCartPaymentMethod paymentMethod);

    GfCartShippingInformation convertMagentoCartShippingInformationToGfCartShippingInformation(MagentoCartShippingInformation shippingInformation);

    MagentoCartItem convertGfCartItemToMagentoCartItem(GfCartItem cartItem);

    MagentoCartAddress convertGfCartAddressToMagentoCartAddress(GfCartAddress cartAddress);

    MagentoCartAddressInformation convertGfCartAddressInformationToMagentoCartAddressInformation(GfCartAddressInformation cartAddress);

    MagentoCartPaymentInformation convertGfCartPaymentInformationToMagentoCartPaymentInformation(GfCartPaymentInformation cartAddress);

    //--shipment
    MagentoShipment convertGfShipmentToMagentoShipment(GfShipment gfShipment);

    MagentoShipmentItem convertGfShipmentItemToMagentoShipmentItem(GfShipmentItem gfShipmentItem);

    MagentoShipmentTrack convertGfShipmentTrackToMagentoShipmentTrack(GfShipmentTrack gfShipmentTrack);

    MagentoShipmentComment convertGfShipmentCommentToMagentoShipmentComment(GfShipmentComment gfShipmentComment);

    MagentoShipOrder convertGfShipOrderToMagentoShipOrder(GfShipOrder gfShipOrder);

    MagentoShipmentItemCreation convertGfShipmentItemCreationToMagentoShipmentItemCreation(GfShipmentItemCreation gfShipmentItemCreation);

    MagentoShipmentCommentCreation convertGfGfShipmentCommentCreationToMagentoShipmentCommentCreation(GfShipmentCommentCreation gfShipmentCommentCreation);

    MagentoShipmentTrackCreation convertGfShipmentTrackCreationToMagentoShipmentTrackCreation(GfShipmentTrackCreation gfShipmentTrackCreation);

    MagentoShipmentPackageCreation convertGfShipmentPackageCreationToMagentoShipmentPackageCreation(GfShipmentPackageCreation gfShipmentPackageCreation);

    GfShipment convertMagentoShipmentToGfShipment(MagentoShipment magentoShipment);

    GfShipmentItem convertMagentoShipmentItemToGfShipmentItem(MagentoShipmentItem magentoShipmentItem);

    GfShipmentTrack convertMagentoShipmentTrackToGfShipmentTrack(MagentoShipmentTrack magentoShipmentTrack);

    GfShipmentComment convertMagentoShipmentCommentToGfShipmentComment(MagentoShipmentComment magentoShipmentComment);

    GfShipmentResp convertMagentoShipmentRespToGfShipmentResp(MagentoShipmentResp magentoShipmentResp);
    //--shipment

    //--order
    MagentoOrderItem from(GfOrderItem orderItem);
    MagentoOrderItemOrderId from(GfOrderItemOrderId itemOrderId);

    GfOrderItem from(MagentoOrderItem orderItem);
    GfOrderItemOrderId from(MagentoOrderItemOrderId itemOrderId);
    GfOrderResp from(MagentoOrderResp orderResp);
    //--order
}
