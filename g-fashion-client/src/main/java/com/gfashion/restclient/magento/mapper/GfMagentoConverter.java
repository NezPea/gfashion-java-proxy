package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;
import com.gfashion.domain.product.GfProductSearchResponse;
import com.gfashion.restclient.magento.MagentoCustomer;
import com.gfashion.restclient.magento.MagentoCustomerAddress;
import com.gfashion.restclient.magento.MagentoProduct;
import com.gfashion.restclient.magento.MagentoProductSearchResponse;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoConverter {
    GfCustomer convertMagentoCustomerToGfCustomer(MagentoCustomer magentoCustomer);
    GfProduct convertMagentoProductToGfProduct(MagentoProduct magentoProduct);
    GfProductSearchResponse convertMagentoProductSearchToGfProductSearch(MagentoProductSearchResponse magentoProductSearchResponse);
    MagentoCustomer convertGfCustomerToMagentoCustomer(GfCustomer gfCustomer);
    GfCustomerAddress convertMagentoCustomerAddressToGfCustomerAddress(MagentoCustomerAddress magentoCustomerAddress);
    MagentoCustomerAddress convertGfCustomerAddressToMagentoCustomerAddress(GfCustomerAddress gfCustomerAddress);
}
