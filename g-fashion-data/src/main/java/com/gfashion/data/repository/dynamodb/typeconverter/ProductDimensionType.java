package com.gfashion.data.repository.dynamodb.typeconverter;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDimensionType {

    private String length;
    private String height;
    private String thickness;
}
