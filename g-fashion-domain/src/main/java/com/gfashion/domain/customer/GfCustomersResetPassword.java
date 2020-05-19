package com.gfashion.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfCustomersResetPassword {

    private String email;
    private String resetToken;
    private String newPassword;
}
