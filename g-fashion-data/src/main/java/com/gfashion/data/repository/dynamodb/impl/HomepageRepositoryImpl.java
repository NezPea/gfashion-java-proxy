package com.gfashion.data.repository.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.KeyPair;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
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

    private final GfDynamodbConverter _mapper = Mappers.getMapper(GfDynamodbConverter.class);

    public CustomizedHomepage getDefaultCustomizedHomepageBatchQuery() {
        CustomizedHomepage customizedHomepage = new CustomizedHomepage();

        // Construct the batch query
        Map<Class<?>, String[]> keyMap = new HashMap<>();
        keyMap.put(GfBrandEntity.class, new String[]{"4d8b996e-7d31-4463-ac32-03e48af71eb9", "22141eab-f9f8-43bb-bc80-3867b4fc8350",
                "cefa93e2-05d9-4f13-8824-7532cf91a544", "0b6bd4e4-d3c1-45ed-80a6-981f4b63e0e2", "53c27c88-f394-4297-8244-c37766905ab1"
        });
        keyMap.put(GfDesignerEntity.class, new String[]{"22f6a794-9a6f-4b1b-b5ff-78c7766f5d0e", "a20fd425-aa13-4f18-834a-38bafce4031b",
                "23fa1c1e-fbcf-466f-adf1-4fc824255954", "a49ba01e-2c6f-49c4-a823-e9dcbbbeee34", "629524b1-d299-4e83-b216-e99afc083764",
                "2826d6c4-6700-41d8-8932-8d8f5d4fe261", "c9d7b743-50c5-4266-9bdf-228924e311ec", "20891b85-a4dd-4ece-990e-bb227e2a316c"
        });
        keyMap.put(GfProductEntity.class, new String[]{"813354b7-5dc0-4874-8231-6c4d00433709", "61af3f6d-c3ad-4a44-af37-21a4e3dc3da2",
                "cc34ed34-51de-4979-9a4d-8a79e51e8d87", "60a08abf-0964-4324-a175-89baf7213145", "03255fe2-ae54-48ba-b20c-a4e4f05a2004",
                "0b41c0d7-8bca-483f-8e15-5885dce3aa65"
        });

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

    public CustomizedHomepage getDefaultCustomizedHomepageReflection() {
        // get recommended brands
        List<HomepageBrand> recommendedBrands = new ArrayList<>();
        getResults(HomepageBrand.class, GfBrandEntity.class, 5)
                .parallelStream().forEach(b ->
                recommendedBrands.add(this._mapper.convertDynamodbBrandToHomeBrand((GfBrandEntity) b)));

        // get recommended designers
        List<HomepageDesigner> recommendedDesigners = new ArrayList<>();
        getResults(HomepageDesigner.class, GfDesignerEntity.class, 8)
                .parallelStream().forEach(d ->
                recommendedDesigners.add(this._mapper
                        .convertDynamodbDesignerToHomeDesigner((GfDesignerEntity) d)));

        // get recommended products
        List<HomepageProduct> recommendedProducts = new ArrayList<>();
        getResults(HomepageProduct.class, GfProductEntity.class, 6)
                .parallelStream().forEach(p ->
                recommendedProducts.add(this._mapper.convertDynamodbProductToHomeProduct((GfProductEntity) p)));

        return CustomizedHomepage.builder()
                .recommendedBrands(recommendedBrands)
                .recommendedDesigners(recommendedDesigners)
                .recommendedProducts(recommendedProducts)
                .followingBrands(recommendedBrands.subList(0, 1))
                .followingDesigners(recommendedDesigners.subList(0, 3))
                .build();
    }

    private <T> PaginatedScanList<T> getResults(Class<?> source, Class<T> target, int limit) {
        DynamoDBScanExpression brandScanExpression = new DynamoDBScanExpression();
        Map<String, Condition> brandConditions = new HashMap<>();
        for (Field field : source.getDeclaredFields()) {
            Condition c = new Condition();
            c.setComparisonOperator("NOT_NULL");
            brandConditions.put(field.getName(), c);
        }
        brandScanExpression.setLimit(limit);
        brandScanExpression.setScanFilter(brandConditions);

        return dynamoDBMapper.scan(target, brandScanExpression);
    }
}
