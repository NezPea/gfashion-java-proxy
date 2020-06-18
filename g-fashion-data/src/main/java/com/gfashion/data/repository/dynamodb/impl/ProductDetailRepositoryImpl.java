package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.KeyPair;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.gfashion.data.*;
import com.gfashion.data.repository.dynamodb.*;
import com.gfashion.data.repository.dynamodb.typeconverter.GfDynamodbConverter;
import com.gfashion.domain.product.GfProduct;
import com.gfashion.domain.productdetail.GfProductShort;
import com.gfashion.domain.productdetail.GfSku;
import com.gfashion.domain.productdetail.ProductDetail;
import com.gfashion.domain.homepage.HomepageDesigner;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDetailRepositoryImpl implements GfProductDetailRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    private GfProductRepository productRepository;
    private GfProductRecRepository productRecRepository;
    private GfSkuCopyRepository skuRepository;

    /*@Value("${aws.samples.brands}")
    private String brands;*/


    private final GfDynamodbConverter _mapper = Mappers.getMapper(GfDynamodbConverter.class);

    public ProductDetail getDefaultProductDetailBatchQuery(String productId, String locale) {


        GfProductEntity gfProductEntity = productRepository.readGfProductEntityById(productId);
        GfProductRecEntity gfProductRecEntity = productRecRepository.readGfProductRecEntityById(productId);
        Map<String, GfSkuCopyEntity> gfSkuEntityMap = skuRepository.readGfSkuCopyEntityByProductId(productId);

        Map<String, GfSku> gfSkuMap = new HashMap<>();
        gfSkuEntityMap.forEach((skuId, skuEntity) -> {
            gfSkuMap.put(skuId, this._mapper.convertDynamodbSkuToDetailSku(skuEntity));
        });
        ProductDetail productDetail = this._mapper.convertDynamodbProductToDetailProduct(gfProductEntity);
        // Construct the batch query

        List<String> productList = gfProductRecEntity.getStyledWithProductList();

        Map<Class<?>, String[]> keyMap = new HashMap<>();

        keyMap.put(GfProductEntity.class, productList.toArray(new String[productList.size()]));


        Map<Class<?>, List<KeyPair>> keyPairForTable = new HashMap<>();

        for (Class<?> T : keyMap.keySet()) {
            List<KeyPair> keyPairList = new ArrayList<>();
            for (String key : keyMap.get(T)) {
                KeyPair keyPair = new KeyPair();
                keyPair.setHashKey(key);
                keyPairList.add(keyPair);
            }
            keyPairForTable.put(T, keyPairList);
        }

        // Batch query
        Map<String, List<Object>> batchResults = dynamoDBMapper.batchLoad(keyPairForTable);

        // Convert the results
        /*List<HomepageBrand> recommendedBrands = new ArrayList<>();
        for (Object entity : batchResults.get("gfBrand")) {
            recommendedBrands.add(this._mapper.convertDynamodbBrandToHomeBrand((GfBrandEntity) entity));
        }
        customizedHomepage.setRecommendedBrands(recommendedBrands);*/

        List<GfProductShort> styledWithProductList = new ArrayList<>();
        for (Object entity : batchResults.get("gfProduct")) {
            styledWithProductList.add(this._mapper.convertDynamodbProductToShortProduct((GfProductEntity) entity));
        }
        productDetail.setStyledWith(styledWithProductList);
        productDetail.setSku(gfSkuMap);

        return productDetail;
    }

}
