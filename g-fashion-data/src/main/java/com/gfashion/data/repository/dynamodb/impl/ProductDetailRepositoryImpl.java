package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.gfashion.data.*;
import com.gfashion.data.repository.dynamodb.*;
import com.gfashion.data.repository.dynamodb.typeconverter.GfDynamodbConverter;
import com.gfashion.domain.productdetail.GfProductShort;
import com.gfashion.domain.productdetail.GfSku;
import com.gfashion.domain.productdetail.ProductDetail;
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
    private GfSkuRepository skuRepository;

    /*@Value("${aws.samples.brands}")
    private String brands;*/


    private final GfDynamodbConverter _mapper = Mappers.getMapper(GfDynamodbConverter.class);

    public ProductDetail getDefaultProductDetailBatchQuery(String productId, String locale) {

        GfProductEntity queryEntity = new GfProductEntity();
        queryEntity.setId(productId);
        GfProductEntity gfProductEntity = (GfProductEntity) readObjectById(queryEntity);

        GfProductRecEntity queryRecEntity = new GfProductRecEntity();
        queryRecEntity.setId(productId);
        GfProductRecEntity gfProductRecEntity = (GfProductRecEntity) readObjectById(queryRecEntity);

        Map<String, GfSkuEntity> gfSkuEntityMap = querySkuByProductId(productId);

        Map<String, GfSku> gfSkuMap = new HashMap<>();
        gfSkuEntityMap.forEach((skuId, skuEntity) -> {
            gfSkuMap.put(skuId, this._mapper.convertDynamodbSkuToDetailSku(skuEntity));
        });
        ProductDetail productDetail = convertDynamodbProductToDetailProduct(gfProductEntity,locale);
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

        List<ProductDetail> styledWithProductList = new ArrayList<>();
        for (Object entity : batchResults.get("gfProduct")) {
            styledWithProductList.add(convertDynamodbProductToDetailProduct(entity,locale));

        }
        productDetail.setStyledWith(styledWithProductList);
        productDetail.setSku(gfSkuMap);

        return productDetail;
    }

    public Object readObjectById(Object entity ) {
        TransactionLoadRequest request = new TransactionLoadRequest();
        request.addLoad(entity);
        return dynamoDBMapper.transactionLoad(request).get(0);
    }

    public Map<String, GfSkuEntity> querySkuByProductId(String ProductId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(ProductId));

        DynamoDBQueryExpression<GfSkuEntity> queryExpression = new DynamoDBQueryExpression<GfSkuEntity>()
                .withKeyConditionExpression("productId = :val1").withExpressionAttributeValues(eav);

        List<GfSkuEntity> GfSkuList = dynamoDBMapper.query(GfSkuEntity.class, queryExpression);


        Map<String, GfSkuEntity> gfSkuEntityMap = new HashMap<>();
        GfSkuList.forEach(sku -> {
            GfSkuEntity GfSkuEntity = (GfSkuEntity) sku;
            gfSkuEntityMap.put(GfSkuEntity.getSkuId(), GfSkuEntity);
        });
        return gfSkuEntityMap;
    }

    public ProductDetail convertDynamodbProductToDetailProduct(Object entity,String locale) {
        if (locale == null || locale.equalsIgnoreCase("en")) {
            return this._mapper.convertDynamodbProductToDetailProductEn((GfProductEntity) entity);
        }else if (locale.equalsIgnoreCase("cn")) {
            return this._mapper.convertDynamodbProductToDetailProductZh((GfProductEntity) entity);
        }
        return null;
    }

}
