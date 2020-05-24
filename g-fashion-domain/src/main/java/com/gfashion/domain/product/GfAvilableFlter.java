package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfAvilableFlter {
    private String code;
    private String name;
    private List<GfAttributeOption> options;
}
