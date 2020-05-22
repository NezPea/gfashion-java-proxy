package com.gfashion.domain.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomepageDesigner {
    private Integer id;
    private String name;
    private String photoUrl;
    private String link;
}
