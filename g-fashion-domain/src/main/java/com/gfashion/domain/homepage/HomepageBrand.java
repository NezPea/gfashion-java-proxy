package com.gfashion.domain.homepage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomepageBrand {
    private String id;
    private String name;
    private String country;
    private String photoUrl;
    private String link;
}
