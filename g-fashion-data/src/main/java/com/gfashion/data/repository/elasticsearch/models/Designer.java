package com.gfashion.data.repository.elasticsearch.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Designer {
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String brief;
}
