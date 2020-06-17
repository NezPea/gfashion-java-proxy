package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;
import com.gfashion.domain.customer.GfCustomerExtensionAttributes;
import com.gfashion.domain.customer.GfCustomerRegion;
import com.gfashion.restclient.magento.customer.MagentoCustomer;
import com.gfashion.restclient.magento.customer.MagentoCustomerAddress;
import com.gfashion.restclient.magento.customer.MagentoCustomerExtensionAttributes;
import com.gfashion.restclient.magento.customer.MagentoCustomerRegion;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoCustomerConverter {
    GfCustomer convertMagentoCustomerToGfCustomer(MagentoCustomer magentoCustomer);

    GfCustomerAddress convertMagentoCustomerAddressToGfCustomerAddress(MagentoCustomerAddress magentoCustomerAddress);

    MagentoCustomerAddress convertGfCustomerAddressToMagentoCustomerAddress(GfCustomerAddress gfCustomerAddress);

    GfCustomerExtensionAttributes convertMagentoCustomerExtensionAttributesToGfCustomerExtensionAttributes(MagentoCustomerExtensionAttributes magentoCustomerExtensionAttributes);

    MagentoCustomerExtensionAttributes convertGfCustomerExtensionAttributesToMagentoCustomerExtensionAttributes(GfCustomerExtensionAttributes gfCustomerExtensionAttributes);

    GfCustomerRegion convertMagentoCustomerRegionToGfCustomerRegion(MagentoCustomerRegion magentoCustomerRegion);

    MagentoCustomerRegion convertGfCustomerRegionToMagentoCustomerRegion(GfCustomerRegion gfCustomerRegion);

}
