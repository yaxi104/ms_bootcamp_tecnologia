package com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tecnologia")
@Getter
@Setter
@NoArgsConstructor
public class TecnologyEntity {

    @Id
    private Long id;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}