package com.gfashion.data.repository.elasticsearch.constants;

public class ElasticSearchConstants {
    private ElasticSearchConstants() throws IllegalAccessException {
        throw new IllegalAccessException("ElasticSearchConstants is a utility class");
    }

    public static final String PRODUCTS_INDEX = "products";
    public static final String PRODUCTS_DOCUMENT_TYPE = "_doc";

    public static final String FILTER_PATH = "filter_path";
    public static final String FILTER = "hits.hits._source";
    public static final String SEARCH_API = "/_search";
    public static final String STATS_API = "/_stats";

    public static final String EMPTY_RESPONSE = "{}";
}
