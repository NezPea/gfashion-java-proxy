package com.gfashion.data.repository.elasticsearch.constant;

public interface Constants {
    String INDEX_PRODUCT = "product";
    String INDEX_DESIGNER = "designer";
    String INDEX_CATEGORY = "category";
    String TYPE = "doc";

    String GROUP_DESIGNER = "designer_group";
    String GROUP_CATEGORY = "category_group";
    String GROUP_TOP_CATEGORY = "top_category_group";
    String GROUP_SALE = "sale_group";

    String SUGGEST_FIELD = "suggest";
    String SUGGEST_NAME = "designer_suggest";

    int ROOT_CATEGORY_LEVEL = 1;
    int TOP_CATEGORY_LEVEL = 2;

    String CATEGORY_DES = "description";
}
