package com.gfashion.data.repository.elasticsearch.model;

import com.gfashion.data.repository.elasticsearch.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.completion.Completion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Constants.INDEX_DESIGNER, type = Constants.TYPE)
public class EsDesigner {
    @Id @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Long)
    private Long designerId;
    private String nameEn;
    private String nameZh;
    private String briefEn;
    private String briefZh;
    private String photoUrl;
    @Transient
    private Long topCategoryId;
    @Transient
    private Boolean sale;
    @Transient
    private Long productCount;

    @CompletionField
    private Completion suggest;
}
