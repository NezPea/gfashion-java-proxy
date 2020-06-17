package com.gfashion.data.service;

import com.gfashion.data.GfCustomServiceEntity;

public interface GfCustomServiceService {

    GfCustomServiceEntity getInfoById(String serviceId);

    GfCustomServiceEntity updateName(String serviceId, String name);
}
