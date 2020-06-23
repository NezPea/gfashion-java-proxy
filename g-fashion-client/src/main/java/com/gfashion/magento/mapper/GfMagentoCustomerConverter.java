package com.gfashion.magento.mapper;

import com.gfashion.domain.customer.GfCustomer;
import com.gfashion.domain.customer.GfCustomerAddress;
import com.gfashion.domain.customer.GfCustomerExtensionAttributes;
import com.gfashion.domain.customer.GfCustomerRegion;
import com.gfashion.magento.entity.customer.MagentoCustomerExtensionAttributes;
import com.gfashion.magento.entity.customer.MagentoCustomerRegion;
import com.gfashion.magento.entity.customer.MagentoCustomer;
import com.gfashion.magento.entity.customer.MagentoCustomerAddress;
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
