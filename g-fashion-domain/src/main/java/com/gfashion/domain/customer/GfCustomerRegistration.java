package com.gfashion.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GfCustomerRegistration {

    private GfCustomer customer;

    private String password;
}
