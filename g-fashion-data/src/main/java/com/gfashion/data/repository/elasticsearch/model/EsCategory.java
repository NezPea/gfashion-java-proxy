package com.gfashion.data.repository.elasticsearch.model;

import com.gfashion.data.repository.elasticsearch.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Constants.INDEX_CATEGORY, type = Constants.TYPE)
public class EsCategory {
    @Id @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Long)
    private Long parentId;
    @Field(type = FieldType.Text)
    private String nameEn;
    @Field(type = FieldType.Text)
    private String nameZh;
    @Field(type = FieldType.Text)
    private String briefEn;
    @Field(type = FieldType.Text)
    private String briefZh;
    @Field(type = FieldType.Boolean)
    private Boolean isActive;
    @Field(type = FieldType.Integer)
    private Integer position;
    @Field(type = FieldType.Integer)
    private Integer level;
    @Field(type = FieldType.Text)
    private String children;
    @Field(type = FieldType.Text)
    private String path;
}
