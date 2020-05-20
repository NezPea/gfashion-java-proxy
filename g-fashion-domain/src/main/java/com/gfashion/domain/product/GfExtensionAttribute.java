package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfExtensionAttribute {

    private List<Integer> website_ids;
    private List<JSONObject> category_links;

}
