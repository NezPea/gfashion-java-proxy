package com.gfashion.domain.productdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetail {

    //产品id,也可叫做spuID,或者父SkuId，这里1个productId对应多个skuID
    private String id;

    private String name;

    //产品条码
    private String productCode;

    //原价
    private Integer price;

    //二次折扣价
    private Integer discountPrice;

    //二次折扣价
    private Integer gClubMemberPrice;

    //币种
    private String currency;

    //是否可以二次打折
    private boolean isSecondDiscount;

    private String keyword;

    //编辑笔记
    private String des;

    //尺码信息
    private String specification;

    private String conservation;

    //售后
    private String deliveryReturn;

    //设计师名称
    private String designerName;

    private String designerLink;

    private List<String> photoUrl;

    private String smallPic;

    private Integer categoryId;

    private List<String> categories;
    //大类
    private Integer topCategoryId;

    //促销
    private boolean sale;

    private Integer purchaseNumber;

    //限购数量 是否可见
    private boolean showLimit;

    private List<String> sizeList;

    private List<ProductDetail> brands;
    private List<ProductDetail> category;
    private List<ProductDetail> styledWith;
    private Map<String,GfSku> sku;
}
