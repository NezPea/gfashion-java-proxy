package com.gfashion.data.repository.dynamodb;

import com.gfashion.domain.homepage.CustomizedHomepage;
import org.springframework.stereotype.Repository;

@Repository
public interface GfHomepageRepository {
    public CustomizedHomepage getDefaultCustomizedHomepageBatchQuery(String lang);
    public CustomizedHomepage getDefaultCustomizedHomepageReflection(String lang);
}
