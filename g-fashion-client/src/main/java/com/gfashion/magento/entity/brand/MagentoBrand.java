package com.gfashion.magento.entity.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoBrand {
    private Integer id;
    private String name;
    private String photoUrl;
    private String link;
}
