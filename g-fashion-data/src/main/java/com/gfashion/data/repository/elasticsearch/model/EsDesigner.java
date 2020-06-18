package com.gfashion.data.repository.elasticsearch.model;

import com.gfashion.data.repository.elasticsearch.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.completion.Completion;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Constants.INDEX_DESIGNER, type = Constants.TYPE)
public class EsDesigner {
    @Id @Field(type = FieldType.Long)
    private Long id;
    private Long designerId;
    private String name_en;
    private String name_zh;
    private String brief_en;
    private String brief_zh;
    private String photoUrl;
    private Integer topCategoryId;
    private Boolean sale;
    private Long productCount;

    @CompletionField
    private Completion suggest;
}
