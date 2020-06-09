package com.gfashion.domain.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCartTotalSegment {

    private String code;
    private String title;
    private Integer value;
    private GfCartTotalSegmentExtensionAttributes extensionAttributes;
    private String area;
}
