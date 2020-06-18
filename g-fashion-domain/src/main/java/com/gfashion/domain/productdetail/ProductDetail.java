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

    private String id;

    //产品id,也可叫做spuID,或者父SkuId，这里1个productId对应多个skuID
    private String productId;

    //产品条码
    private String productCode;

    //产品名称
    private String productName;

    //语言
    private String language;

    //三级分类id 此处考虑是否拆分成3个字段，third second first，或者添加一张category表。
    private Integer categoryId;

    //设计师id
    private String designerId;

    //设计师名称
    private String designerName;

    //设计师链接
    private String designerLink;

    //大类
    private String mainCategory;

    //币种
    private String currency;

    //是否第三方发货
    private boolean isDeliveryByThirdParty;

    //原价
    private BigDecimal price;

    //gClub会员价
    private BigDecimal gclubMemberPrice;

    //二次折扣价
    private BigDecimal discountPrice;

    //图片地址
    private List<String> images;

    //限购数量
    private Integer purchaseNumber;

    //限购数量 是否可见
    private boolean isPurchaseNumberVisible;

    //编辑笔记
    private String editNotes;

    //细节与养护
    private String conservation;

    //尺码信息
    private String specification;

    //购买须知
    private String postSale;

    //是否可以二次打折
    private boolean isDiscount;

    private String photoUrl;

    private Boolean isFeatured;
    private List<GfProductShort> brands;
    private List<GfProductShort> category;
    private List<GfProductShort> styledWith;
    private Map<String,GfSku> sku;
}
