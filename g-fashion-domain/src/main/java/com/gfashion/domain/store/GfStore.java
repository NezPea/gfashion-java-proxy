package com.gfashion.domain.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfStore {
    private Integer id;
    private String code;
    private String name;
    private Integer website_id;
    private Integer store_group_id;
    private Integer is_active;
}

