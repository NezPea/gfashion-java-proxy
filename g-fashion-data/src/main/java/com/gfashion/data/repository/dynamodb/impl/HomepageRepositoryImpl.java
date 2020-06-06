package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.KeyPair;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.gfashion.data.GfBrandEntity;
import com.gfashion.data.GfDesignerEntity;
import com.gfashion.data.GfProductEntity;
import com.gfashion.data.repository.dynamodb.GfHomepageRepository;
import com.gfashion.data.repository.dynamodb.typeconverter.GfDynamodbConverter;
import com.gfashion.domain.homepage.CustomizedHomepage;
import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.domain.homepage.HomepageProduct;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HomepageRepositoryImpl implements GfHomepageRepository {

    @Autowired
    public DynamoDBMapper dynamoDBMapper;

    @Value("${aws.samples.brands}")
    private String brands;

    @Value("${aws.samples.designers}")
    private String designers;

    @Value("${aws.samples.products}")
    private String products;

    @Value("${aws.samples.brandsCn}")
    private String brandsCn;

    @Value("${aws.samples.designersCn}")
    private String designersCn;

    @Value("${aws.samples.productsCn}")
    private String productsCn;

    private final GfDynamodbConverter _mapper = Mappers.getMapper(GfDynamodbConverter.class);

    public CustomizedHomepage getDefaultCustomizedHomepageBatchQuery(String lang) {
        CustomizedHomepage customizedHomepage = new CustomizedHomepage();

        // Construct the batch query
        Map<Class<?>, String[]> keyMap = new HashMap<>();
        if (lang == null || lang.equalsIgnoreCase("en")) {
            keyMap.put(GfBrandEntity.class, brands.split(","));
            keyMap.put(GfDesignerEntity.class, designers.split(","));
            keyMap.put(GfProductEntity.class, products.split(","));
        } else if (lang.equalsIgnoreCase("cn")) {
            // Batch load the corresponding records in Chinese
            keyMap.put(GfBrandEntity.class, brandsCn.split(","));
            keyMap.put(GfDesignerEntity.class, designersCn.split(","));
            keyMap.put(GfProductEntity.class, productsCn.split(","));
        }

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
        List<HomepageBrand> recommendedBrands = new ArrayList<>();
        for (Object entity : batchResults.get("gfBrand")) {
            recommendedBrands.add(this._mapper.convertDynamodbBrandToHomeBrand((GfBrandEntity) entity));
        }
        customizedHomepage.setRecommendedBrands(recommendedBrands);

        List<HomepageDesigner> recommendedDesigners = new ArrayList<>();
        for (Object entity : batchResults.get("gfDesigner")) {
            recommendedDesigners.add(this._mapper.convertDynamodbDesignerToHomeDesigner((GfDesignerEntity) entity));
        }
        customizedHomepage.setRecommendedDesigners(recommendedDesigners);

        List<HomepageProduct> recommendedProducts = new ArrayList<>();
        for (Object entity : batchResults.get("gfProduct")) {
            recommendedProducts.add(this._mapper.convertDynamodbProductToHomeProduct((GfProductEntity) entity));
        }
        customizedHomepage.setRecommendedProducts(recommendedProducts);

        customizedHomepage.setFollowingBrands(recommendedBrands.subList(0, 1));
        customizedHomepage.setFollowingDesigners(recommendedDesigners.subList(0, 3));

        return customizedHomepage;
    }

    public CustomizedHomepage getDefaultCustomizedHomepageReflection(String lang) {
        lang = lang == null ? "en" : lang;

        // get recommended brands
        List<HomepageBrand> recommendedBrands = new ArrayList<>();
        getResults(HomepageBrand.class, GfBrandEntity.class, 5, lang)
                .parallelStream().forEach(b ->
                recommendedBrands.add(this._mapper.convertDynamodbBrandToHomeBrand((GfBrandEntity) b)));

        // get recommended designers
        List<HomepageDesigner> recommendedDesigners = new ArrayList<>();
        getResults(HomepageDesigner.class, GfDesignerEntity.class, 8, lang)
                .parallelStream().forEach(d ->
                recommendedDesigners.add(this._mapper
                        .convertDynamodbDesignerToHomeDesigner((GfDesignerEntity) d)));

        // get recommended products
        List<HomepageProduct> recommendedProducts = new ArrayList<>();
        getResults(HomepageProduct.class, GfProductEntity.class, 6, lang)
                .parallelStream().forEach(p ->
                recommendedProducts.add(this._mapper.convertDynamodbProductToHomeProduct((GfProductEntity) p)));

        return CustomizedHomepage.builder()
                .recommendedBrands(recommendedBrands)
                .recommendedDesigners(recommendedDesigners)
                .recommendedProducts(recommendedProducts)
                .followingBrands(recommendedBrands.subList(0, Math.min(1, recommendedBrands.size())))
                .followingDesigners(recommendedDesigners.subList(0, Math.min(3, recommendedDesigners.size())))
                .build();
    }

    private <T> PaginatedScanList<T> getResults(Class<?> source, Class<T> target, int limit, String lang) {
        DynamoDBScanExpression brandScanExpression = new DynamoDBScanExpression();
        Map<String, Condition> brandConditions = new HashMap<>();
        for (Field field : source.getDeclaredFields()) {
            Condition c = new Condition();
            c.setComparisonOperator(ComparisonOperator.NOT_NULL);
            brandConditions.put(field.getName(), c);
        }
        // language condition
        Condition languageCondition = new Condition();
        languageCondition.setComparisonOperator(ComparisonOperator.EQ);
        List<AttributeValue> valueList = new ArrayList<AttributeValue>();
        valueList.add(new AttributeValue(lang));
        languageCondition.setAttributeValueList(valueList);
        brandConditions.put("language", languageCondition);

        brandScanExpression.setLimit(limit);
        brandScanExpression.setScanFilter(brandConditions);

        return dynamoDBMapper.scan(target, brandScanExpression);
    }
}
