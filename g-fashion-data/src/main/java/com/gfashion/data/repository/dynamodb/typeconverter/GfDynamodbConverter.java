package com.gfashion.data.repository.dynamodb.typeconverter;

import com.gfashion.data.*;
import com.gfashion.domain.homepage.HomepageBrand;
import com.gfashion.domain.homepage.HomepageDesigner;
import com.gfashion.domain.homepage.HomepageProduct;
import com.gfashion.domain.productdetail.GfProductShort;
import com.gfashion.domain.productdetail.GfSku;
import com.gfashion.domain.productdetail.ProductDetail;
import com.google.gson.Gson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface GfDynamodbConverter {
    HomepageBrand convertDynamodbBrandToHomeBrand(GfBrandEntity gfBrandEntity);

    @Mapping(source = "cooperatingBrands", target = "cooperatingBrands", qualifiedByName = "getCooperatingBrandsFromAttribute")
    HomepageDesigner convertDynamodbDesignerToHomeDesigner(GfDesignerEntity gfDesignerEntity);

    @Named("getCooperatingBrandsFromAttribute")
    public static List<String> getCooperatingBrandsFromAttribute(String cooperatingBrands){
        Gson gson = new Gson();
        String[] brands = gson.fromJson(cooperatingBrands, String[].class);
        return Arrays.asList(brands);
    }

    HomepageProduct convertDynamodbProductToHomeProduct(GfProductEntity gfProductEntity);


    ProductDetail convertDynamodbProductToDetailProduct(GfProductEntity gfProductEntity);
    GfProductShort convertDynamodbProductToShortProduct(GfProductEntity gfProductEntity);
    GfSku convertDynamodbSkuToDetailSku(GfSkuCopyEntity gfSkuEntity);
}
