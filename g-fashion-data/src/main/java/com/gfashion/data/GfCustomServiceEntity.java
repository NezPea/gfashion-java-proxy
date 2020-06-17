package com.gfashion.data;


import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "gfCustomService")
public class GfCustomServiceEntity {

    @Id
    @GeneratedValue
    @Column(name = "serviceId")
    private String serviceId;

    @Column(name = "name")
    private String name;




}
