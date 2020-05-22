package com.gfashion.restclient.magento.homepage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class MagentoCategory {
    private Integer id;
    private Integer parent_id;
    private String name;
    private Boolean is_active;
    private Integer position;
    private Integer level;
    private String children;
    private String created_at;
    private String updated_at;
    private String path;
    private Boolean include;
    private List<Map<String, String>> custom_attributes;
}
