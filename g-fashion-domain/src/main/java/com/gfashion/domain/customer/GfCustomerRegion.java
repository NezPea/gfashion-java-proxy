package com.gfashion.domain.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCustomerRegion {
    private String region_code;
    private String region;
    private Integer region_id;
    private JSONObject extension_attributes;
}
