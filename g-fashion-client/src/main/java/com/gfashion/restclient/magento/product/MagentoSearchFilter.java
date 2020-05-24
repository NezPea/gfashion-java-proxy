package com.gfashion.restclient.magento.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagentoSearchFilter {

    private String field;
    private String value;
    private String condition_type;

}
