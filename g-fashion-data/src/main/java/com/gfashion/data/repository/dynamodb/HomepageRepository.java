package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.entity.homepage.GfCustomizedHomepageEntity;

public interface HomepageRepository {

    GfCustomizedHomepageEntity getCustomizedHomepageByCustomerId(String customerId);

}
