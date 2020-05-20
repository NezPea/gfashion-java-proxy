package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductMediaGalleryEntries {

    private Integer id;
    private String media_type;
    private String label;
    private Integer position;
    private Boolean disabled;
    private List <String> types;
    private String file;
    private JSONObject content;
    private JSONObject extension_attributes;

}
