package com.gfashion.data.repository.dynamodb.Interface;

import com.gfashion.domain.productdetail.ProductDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface GfProductDetailRepository {
    public ProductDetail getDefaultProductDetailBatchQuery(String productId, String locale);
}
