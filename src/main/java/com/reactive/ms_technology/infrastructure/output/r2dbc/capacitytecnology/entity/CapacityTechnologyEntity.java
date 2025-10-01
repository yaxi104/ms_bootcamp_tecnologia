package com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "CAPACIDAD_TECNOLOGIAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CapacityTechnologyEntity {

    @Id
    private Long id;

    @Column("id_tecnologia")
    private Long technologyId;

    @Column("id_capacidad")
    private Long capacityId;

}