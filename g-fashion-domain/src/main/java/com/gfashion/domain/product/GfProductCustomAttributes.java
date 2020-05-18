package com.gfashion.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfProductCustomAttributes {
    private String attribute_code;
    private String value;
}
