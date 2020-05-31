package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.GfVendorEntity;

public interface VendorRepository {

    GfVendorEntity createGfVendorEntity(GfVendorEntity GfVendorEntity);

    GfVendorEntity readGfVendorEntity(String GfVendorEntityId);

    GfVendorEntity updateGfVendorEntity(GfVendorEntity GfVendorEntity);

    void deleteGfVendorEntity(String GfVendorEntityId);
}
