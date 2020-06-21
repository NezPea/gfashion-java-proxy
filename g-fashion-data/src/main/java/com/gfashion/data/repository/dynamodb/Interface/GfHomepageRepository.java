package com.gfashion.data.repository.dynamodb.Interface;

import com.gfashion.domain.homepage.CustomizedHomepage;
import org.springframework.stereotype.Repository;

@Repository
public interface GfHomepageRepository {
    public CustomizedHomepage getDefaultCustomizedHomepageBatchQuery(String locale);
    public CustomizedHomepage getDefaultCustomizedHomepageReflection(String locale);
}
