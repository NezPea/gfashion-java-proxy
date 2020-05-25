package com.gfashion.domain.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomepageDesigner {
    private Integer id;
    private String name;
    private List<String> cooperatingBrands;
    private String country;
    private String photoUrl;
}
