package com.reactive.ms_technology.infrastructure.output.r2dbc.technology.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "TECNOLOGIA")
@Getter
@Setter
@NoArgsConstructor
public class TechnologyEntity {

    @Id
    private Long id;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}