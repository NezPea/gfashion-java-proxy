package com.gfashion.data;


import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "gf_custom_service")
public class GfCustomServiceEntity {

    @Id
    @GeneratedValue
    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "name")
    private String name;




}
