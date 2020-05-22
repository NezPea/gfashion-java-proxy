package com.gfashion.restclient.magento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoStoreGroup {
    private Integer id;
    private Integer website_id;
    private Integer root_category_id;
    private Integer default_store_id;
    private String name;
    private String code;
}
