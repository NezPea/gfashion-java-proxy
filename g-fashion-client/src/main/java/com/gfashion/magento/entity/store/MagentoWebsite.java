package com.gfashion.magento.entity.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoWebsite {
    private Integer id;
    private String code;
    private String name;
    private Integer default_group_id;
}
