package com.gfashion.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "gfProduct")
public class GfProductEntity {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "language")
    private String language;

    @DynamoDBAttribute(attributeName = "keyword")
    private String keyword;

    @DynamoDBAttribute(attributeName = "brandId")
    private String brandId;

    @DynamoDBAttribute(attributeName = "designerId")
    private String designerId;

    @DynamoDBAttribute(attributeName = "vendorId")
    private String vendorId;

    @DynamoDBAttribute(attributeName = "productDelete")
    private String productDelete;

    @DynamoDBAttribute(attributeName = "productBrief")
    private String productBrief;

    @DynamoDBAttribute(attributeName = "productCategory")
    private String productCategory;

    @DynamoDBAttribute(attributeName = "productCreatedAt")
    private String productCreatedAt;

    @DynamoDBAttribute(attributeName = "productUpdatedAt")
    private String productUpdatedAt;

    // for GfAttributeOption class
    @DynamoDBAttribute(attributeName = "isChecked")
    private String isChecked;

    // for GfChannelProduct class
    @DynamoDBAttribute(attributeName = "sku")
    private String sku;

    @DynamoDBAttribute(attributeName = "price")
    private BigDecimal price;

    @DynamoDBAttribute(attributeName = "file")
    private String file;

    // for GfEvaAttribute class
    @DynamoDBAttribute(attributeName = "totalCount")
    private Integer totalCount;

    // for GfEvaAttributeOption class
    @DynamoDBAttribute(attributeName = "attributeId")
    private Integer attributeId;

    @DynamoDBAttribute(attributeName = "attributeCode")
    private String attributeCode;

    @DynamoDBAttribute(attributeName = "scope")
    private String scope;

    @DynamoDBAttribute(attributeName = "frontendInput")
    private String frontendInput;

    // for GfSearchFilter class
    @DynamoDBAttribute(attributeName = "conditionType")
    private String conditionType;


    @DynamoDBAttribute(attributeName = "attributeSetId")
    private Integer attributeSetId;

    @DynamoDBAttribute(attributeName = "status")
    private Integer status;

    @DynamoDBAttribute(attributeName = "visibility")
    private Integer visibility;

    @DynamoDBAttribute(attributeName = "typeId")
    private String typeId;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private String updatedAt;

    @DynamoDBAttribute(attributeName = "weight")
    private Double weight;

    @DynamoDBAttribute(attributeName = "purchaseNumberLimit")
    private Integer purchaseNumberLimit; // 每个账号至多购买x件

    // for GfProductGategory class
    @DynamoDBAttribute(attributeName = "parentId")
    private Integer parentId;

    @DynamoDBAttribute(attributeName = "isActive")
    private Boolean isActive;

    @DynamoDBAttribute(attributeName = "position")
    private Integer position;

    @DynamoDBAttribute(attributeName = "level")
    private Integer level;

    @DynamoDBAttribute(attributeName = "children")
    private String children;

    @DynamoDBAttribute(attributeName = "path")
    private String path;

    @DynamoDBAttribute(attributeName = "include")
    private Boolean include;

    // for GfProductLink class

    @DynamoDBAttribute(attributeName = "linkType")
    private String linkType;

    @DynamoDBAttribute(attributeName = "linkedProductSku")
    private String linkedProductSku;

    @DynamoDBAttribute(attributeName = "linkedProductType")
    private String linkedProductType;

    @DynamoDBAttribute(attributeName = "photoUrl")
    private String photoUrl;

    @DynamoDBAttribute(attributeName = "isFeatured")
    private Boolean isFeatured;
//    private List<GfProductCustomAttribute> custom_attributes;
//    private JSONObject extension_attributes;
//    private List<GfMediaGalleryEntry> media_gallery_entries;  // 媒体图片资源库，包含图片地址
//    private List<GfProductCustomAttribute> custom_attributes; // 自定义属性：可能包含颜色、描述、缩略图、价格
//    private List<GfProductLink> product_links; // 关联产品
//    @DynamoDBAttribute(attributeName = "extension_attributes")
//    private GfMediaGalleryEntry extension_attributes; // 扩展属性
}
