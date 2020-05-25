package com.gfashion.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "GF_DESIGNER")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GfDesignerEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

}
