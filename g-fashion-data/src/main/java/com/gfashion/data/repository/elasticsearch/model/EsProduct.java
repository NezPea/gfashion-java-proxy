package com.gfashion.data.repository.elasticsearch.model;

import com.gfashion.data.repository.elasticsearch.constant.Constants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@Document(indexName = Constants.INDEX_PRODUCT, type = Constants.TYPE)
public class EsProduct {
    @Id
    private String id;
    private String name;
    private Integer price;
    private String keyword;
    private String brief;
    private String sku;
    private String brandName;
    private Integer isFeatured;
    private String photoUrl;
    private String language;
    private String brandId;
    private String vendorId;
    private String designerId;
    private String[] categories;
    private String category;
    private String gender;
    private Integer sale;
    private String size;
}
