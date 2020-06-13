package com.gfashion.data.repository.mysql;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity // This tells Hibernate to make a table out of this class
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gfProduct")
public class GfProductMySqlEntity {

    @Id
    private String id;

    private String name;

    private String language;

    private String keyword;

    private String brandId;

    private String designerId;

    private String vendorId;

    private String productDelete;

    private String productBrief;

    private String productCategory;

    private String productCreatedAt;

    private String productUpdatedAt;

    // for GfAttributeOption class
    private String isChecked;

    // for GfChannelProduct class
    private String sku;

    private BigDecimal price;

    private String file;

    // for GfEvaAttribute class
    private Integer totalCount;

    // for GfEvaAttributeOption class
    private Integer attributeId;

    private String attributeCode;

    private String scope;

    private String frontendInput;

    // for GfSearchFilter class
    private String conditionType;


    private Integer attributeSetId;

    private Integer status;

    private Integer visibility;

    private String typeId;

    private String createdAt;

    private String updatedAt;

    private Double weight;

    private Integer purchaseNumberLimit; // 每个账号至多购买x件

    // for GfProductGategory class
    private Integer parentId;

    private Boolean isActive;

    private Integer position;

    private Integer level;

    private String children;

    private String path;

    private Boolean include;

    // for GfProductLink class

    private String linkType;

    private String linkedProductSku;

    private String linkedProductType;

    private String photoUrl;

    private Boolean isFeatured;
//    private List<GfProductCustomAttribute> custom_attributes;
//    private JSONObject extension_attributes;
//    private List<GfMediaGalleryEntry> media_gallery_entries;  // 媒体图片资源库，包含图片地址
//    private List<GfProductCustomAttribute> custom_attributes; // 自定义属性：可能包含颜色、描述、缩略图、价格
//    private List<GfProductLink> product_links; // 关联产品
//    @DynamoDBAttribute(attributeName = "extension_attributes")
//    private GfMediaGalleryEntry extension_attributes; // 扩展属性
}
