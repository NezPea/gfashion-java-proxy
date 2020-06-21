package com.gfashion.magento.mapper;

import com.gfashion.domain.store.GfStore;
import com.gfashion.domain.store.GfStoreConfig;
import com.gfashion.domain.store.GfStoreGroup;
import com.gfashion.domain.store.GfWebsite;
import com.gfashion.magento.entity.store.MagentoStore;
import com.gfashion.magento.entity.store.MagentoStoreConfig;
import com.gfashion.magento.entity.store.MagentoStoreGroup;
import com.gfashion.magento.entity.store.MagentoWebsite;
import org.mapstruct.Mapper;

@Mapper
public interface GfMagentoStoreConverter {

    GfStore[] convertMagentoStoreToGfStoreArray(MagentoStore[] magentoStore);

    GfWebsite[] convertMagentoWebsiteToGfWebsiteArray(MagentoWebsite[] magentoWebsites);

    GfStoreGroup[] convertMagentoStoreGroupToGfStoreGroupArray(MagentoStoreGroup[] magentoStoreGroups);

    GfStoreConfig[] convertMagentoStoreConfigToGfStoreConfigArray(MagentoStoreConfig[] magentoStoreConfigs);

}
