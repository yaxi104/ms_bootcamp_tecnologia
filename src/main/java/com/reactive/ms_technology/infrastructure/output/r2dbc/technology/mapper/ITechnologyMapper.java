package com.reactive.ms_technology.infrastructure.output.r2dbc.technology.mapper;

import com.reactive.ms_technology.domain.model.Technology;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.entity.TechnologyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITechnologyMapper {

    Technology toDomain(TechnologyEntity entity);

    TechnologyEntity toEntity(Technology domain);
}