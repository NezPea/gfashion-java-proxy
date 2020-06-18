package com.gfashion.domain.gfproduct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gfashion.domain.product.GfExtensionAttribute;
import com.gfashion.domain.product.GfMediaGalleryEntry;
import com.gfashion.domain.product.GfProductCustomAttribute;
import com.gfashion.domain.product.GfProductLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProduct {

    private String id;
    private String spuId;
    private String skuId;
    private String brand;
    private String name;
    private Integer categoryId;
    private String designerName;
    private String designerLink;
    private String price;
    private String gclubMemberPrice;
    private String discountPrice;
    private String currency;
    private String images;
    private String purchaseNumber;
    private boolean deliveryByThirdparty;
    private String description;
    private List<GfProductShort> brands;
    private List<GfProductShort> category;
    private List<GfProductShort> styledWith;
    private Map<String,GfSku> sku;


}
