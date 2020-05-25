package com.gfashion.domain.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomepageBrand {
    private Integer id;
    private String name;
    private String country;
    private String photoUrl;
    private String link;
}
