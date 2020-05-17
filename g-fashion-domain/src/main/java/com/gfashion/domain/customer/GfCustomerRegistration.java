package com.gfashion.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCustomerRegistration {

    private GfCustomer customer;

    private String password;
}
