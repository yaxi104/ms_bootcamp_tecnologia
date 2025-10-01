package com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.mapper;

import com.reactive.ms_technology.domain.model.CapacityTechnology;
import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.entity.CapacityTechnologyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICapacityTechnologyMapper {

    CapacityTechnology toDomain(CapacityTechnologyEntity entity);

    CapacityTechnologyEntity toEntity(CapacityTechnology domain);
}