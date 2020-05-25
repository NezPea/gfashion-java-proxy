package com.gfashion.data.repository.dynamodb;

import com.gfashion.data.entity.homepage.GfCustomizedHomepageEntity;

public interface HomepageDynamodbRepository {

    GfCustomizedHomepageEntity getCustomizedHomepageByCustomerId(String customerId);

}
