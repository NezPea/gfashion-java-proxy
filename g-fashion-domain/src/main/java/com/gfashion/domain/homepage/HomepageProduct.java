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
    private String id;
    private String productName;
    private String photoUrl;
    private Boolean isFeatured;
}
