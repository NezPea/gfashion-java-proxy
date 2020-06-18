package com.gfashion.data.repository.dynamodb;

import com.gfashion.domain.homepage.CustomizedHomepage;
import org.springframework.stereotype.Repository;

@Repository
public interface GfProductDetailRepository {
    public CustomizedHomepage getDefaultCustomizedHomepageBatchQuery(String locale);
    public CustomizedHomepage getDefaultCustomizedHomepageReflection(String locale);
}
