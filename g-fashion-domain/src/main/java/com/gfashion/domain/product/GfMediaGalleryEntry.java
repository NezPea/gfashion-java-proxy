package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfMediaGalleryEntry {

    private Integer id;
    private String media_type;
    private String label;
    private Integer position;
    private boolean disabled;
    private List<String> types;
    private String file;

}
