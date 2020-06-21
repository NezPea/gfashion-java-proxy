package com.gfashion.magento.mapper;

import com.gfashion.domain.cart.*;
import com.gfashion.magento.entity.cart.*;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoCartConverter {

    GfCart convertMagentoCartToGfCart(MagentoCart cart);

    GfCartItem convertMagentoCartItemToGfCartItem(MagentoCartItem cartItem);

    GfCartEstimateShippingMethod convertMagentoCartEstimateShippingMethodToGfCartEstimateShippingMethod(MagentoCartEstimateShippingMethod estimateShippingMethod);

    GfCartPaymentMethod convertMagentoCartPaymentMethodToGfCartPaymentMethod(MagentoCartPaymentMethod paymentMethod);

    GfCartShippingInformation convertMagentoCartShippingInformationToGfCartShippingInformation(MagentoCartShippingInformation shippingInformation);

    MagentoCartItem convertGfCartItemToMagentoCartItem(GfCartItem cartItem);

    MagentoCartAddress convertGfCartAddressToMagentoCartAddress(GfCartAddress cartAddress);

    MagentoCartAddressInformation convertGfCartAddressInformationToMagentoCartAddressInformation(GfCartAddressInformation cartAddress);

    MagentoCartPaymentInformation convertGfCartPaymentInformationToMagentoCartPaymentInformation(GfCartPaymentInformation cartAddress);
}
