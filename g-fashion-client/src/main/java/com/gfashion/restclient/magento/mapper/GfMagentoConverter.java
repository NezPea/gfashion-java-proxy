package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;
import com.gfashion.restclient.magento.MagentoCustomer;
import com.gfashion.restclient.magento.MagentoCustomerAddress;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoConverter {
    GfCustomer convertMagentoCustomerToGfCustomer(MagentoCustomer magentoCustomer);
    MagentoCustomer convertGfCustomerToMagentoCustomer(GfCustomer gfCustomer);
    GfCustomerAddress convertMagentoCustomerAddressToGfCustomerAddress(MagentoCustomerAddress magentoCustomerAddress);
    MagentoCustomerAddress convertGfCustomerAddressToMagentoCustomerAddress(GfCustomerAddress gfCustomerAddress);
}
