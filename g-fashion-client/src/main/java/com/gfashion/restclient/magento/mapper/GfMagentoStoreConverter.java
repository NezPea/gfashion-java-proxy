package com.gfashion.restclient.magento.mapper;

import com.gfashion.domain.store.GfStore;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.domain.store.GfStoreGroup;
import com.gfashion.domain.store.GfWebsite;
import com.gfashion.restclient.magento.MagentoStore;
import com.gfashion.restclient.magento.MagentoStoreConfig;
import com.gfashion.restclient.magento.MagentoStoreGroup;
import com.gfashion.restclient.magento.MagentoWebsite;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoStoreConverter {

    GfStore[] convertMagentoStoreToGfStoreArray(MagentoStore[] magentoStore);

    GfWebsite[] convertMagentoWebsiteToGfWebsiteArray(MagentoWebsite[] magentoWebsites);

    GfStoreGroup[] convertMagentoStoreGroupToGfStoreGroupArray(MagentoStoreGroup[] magentoStoreGroups);

    GfStoreConfig[] convertMagentoStoreConfigToGfStoreConfigArray(MagentoStoreConfig[] magentoStoreConfigs);

}
