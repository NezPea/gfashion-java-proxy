package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.cart.*;
import com.gfashion.restclient.magento.cart.*;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoCartConverter {

    GfCart convertMagentoCartToGfCart(MagentoCart cart);

    GfCartItem convertMagentoCartItemToGfCartItem(MagentoCartItem cartItem);

    GfCartEstimateShippingMethod convertMagentoCartEstimateShippingMethodToGfCartEstimateShippingMethod(MagentoCartEstimateShippingMethod estimateShippingMethod);

    GfCartPaymentMethod convertMagentoCartPaymentMethodToGfCartPaymentMethod(MagentoCartPaymentMethod paymentMethod);

    GfCartShippingInfo convertMagentoCartShippingInfoToGfCartShippingInfo(MagentoCartShippingInformation shippingInfo);

    GfCartAdyenPaymentMethod convertMagentoCartAdyenPaymentMethodToGfCartAdyenPaymentMethod(MagentoCartAdyenPaymentMethod paymentMethod);

    GfCartAdyenPaymentStatus convertMagentoCartAdyenPaymentStatusToGfCartAdyenPaymentStatus(MagentoCartAdyenPaymentStatus paymentStatus);

    MagentoCartItem convertGfCartItemToMagentoCartItem(GfCartItem cartItem);

    MagentoCartAddress convertGfCartAddressToMagentoCartAddress(GfCartAddress cartAddress);

    MagentoCartAddressInformation convertGfCartAddressInformationToMagentoCartAddressInformation(GfCartAddressInformation cartAddress);

    MagentoCartPaymentInfo convertGfCartPaymentInfoToMagentoCartPaymentInfo(GfCartPaymentInfo paymentInfo);

    MagentoCartAdyenPaymentInfo convertGfCartAdyenPaymentInfoToMagentoCartAdyenPaymentInfo(GfCartAdyenPaymentInfo paymentInfo);

    MagentoCartAdyenThreeDS2Process convertGfCartAdyenThreeDS2ProcessToMagentoCartAdyenThreeDS2Process(GfCartAdyenThreeDS2Process threeDS2Process);
}
