package com.gfashion.data.repository.dynamodb.typeconverter;

import com.gfashion.data.*;
import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.domain.homepage.HomepageProduct;
import com.gfashion.domain.productdetail.GfSku;
import com.gfashion.domain.productdetail.ProductDetail;
import com.google.gson.Gson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface GfDynamodbConverter {
    HomepageBrand convertDynamodbBrandToHomeBrand(GfBrandEntity gfBrandEntity);

    @Mapping(source = "cooperatingBrands", target = "cooperatingBrands", qualifiedByName = "getCooperatingBrandsFromAttribute")
    HomepageDesigner convertDynamodbDesignerToHomeDesigner(GfDesignerEntity gfDesignerEntity);

    @Named("getCooperatingBrandsFromAttribute")
    public static List<String> getCooperatingBrandsFromAttribute(String cooperatingBrands) {
        Gson gson = new Gson();
        String[] brands = gson.fromJson(cooperatingBrands, String[].class);
        return Arrays.asList(brands);
    }

    @Mapping(source = "nameEn", target = "name")
    @Mapping(source = "smallPic", target = "photoUrl")
    HomepageProduct convertDynamodbProductToHomeProduct(GfProductEntity gfProductEntity);

    @Mapping(source = "nameEn", target = "name")
    @Mapping(source = "desEn", target = "des")
    @Mapping(source = "specificationEn", target = "specification")
    @Mapping(source = "conservationEn", target = "conservation")
    @Mapping(source = "deliveryReturnEn", target = "deliveryReturn")
    ProductDetail convertDynamodbProductToDetailProductEn(GfProductEntity gfProductEntity);

    @Mapping(source = "nameZh", target = "name")
    @Mapping(source = "desZh", target = "des")
    @Mapping(source = "specificationZh", target = "specification")
    @Mapping(source = "conservationZh", target = "conservation")
    @Mapping(source = "deliveryReturnZh", target = "deliveryReturn")
    ProductDetail convertDynamodbProductToDetailProductZh(GfProductEntity gfProductEntity);

    GfSku convertDynamodbSkuToDetailSku(GfSkuEntity gfSkuEntity);
}
