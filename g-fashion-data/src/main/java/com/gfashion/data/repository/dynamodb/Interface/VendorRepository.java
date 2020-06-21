package com.gfashion.data.repository.dynamodb.Interface;

import com.gfashion.data.repository.dynamodb.entity.GfVendorEntity;

public interface VendorRepository {

    GfVendorEntity createGfVendorEntity(GfVendorEntity GfVendorEntity);

    GfVendorEntity readGfVendorEntity(String GfVendorEntityId);

    GfVendorEntity updateGfVendorEntity(GfVendorEntity GfVendorEntity);

    void deleteGfVendorEntity(String GfVendorEntityId);
}
