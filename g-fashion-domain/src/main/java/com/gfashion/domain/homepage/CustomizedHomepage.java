package com.gfashion.domain.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomizedHomepage {
    private Integer id;
    private List<HomepageProduct> recommendedProducts;
    private List<HomepageDesigner> recommendedDesigners;
    private List<HomepageBrand> recommendedBrands;

    private List<HomepageDesigner> followingDesigners;
    private List<HomepageBrand> followingBrands;
}
