package com.gfashion.domain.customer;

import com.gfashion.domain.GfResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCustomerLoginResponse extends GfResponse {
    private String token;
}
