package com.gfashion.data.repository.elasticsearch.model;

import com.gfashion.data.repository.elasticsearch.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Constants.INDEX_DESIGNER, type = Constants.TYPE)
public class EsDesigner {
    @Id
    private String id;
    private List<String> cooperatingBrands;
    private String name;
    private String country;
    private String photoUrl;
    private String language;
}
