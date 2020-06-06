package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartBundleOption {

    private Integer optionId;
    private Integer optionQty;
    private List<Integer> optionSelections;
    private JSONObject extensionAttributes;
}
