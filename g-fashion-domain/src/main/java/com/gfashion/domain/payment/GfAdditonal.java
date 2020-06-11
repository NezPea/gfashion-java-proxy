package com.gfashion.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfAdditonal {
    private String method;
    private GfAdditionalData gfAdditionalData;
}
