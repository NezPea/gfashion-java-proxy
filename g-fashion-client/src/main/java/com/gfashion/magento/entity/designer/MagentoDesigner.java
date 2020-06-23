package com.gfashion.magento.entity.designer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoDesigner {
    private Integer id;
    private String name;
    private String photoUrl;
    private String country;
}