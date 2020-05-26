package com.gfashion.domain.homepage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomepageProduct {
    private Integer id;
    private String name;
    private String photoUrl;
    private Boolean isFeatured;
}
