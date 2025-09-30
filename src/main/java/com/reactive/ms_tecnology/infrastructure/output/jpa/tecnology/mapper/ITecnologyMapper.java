package com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.mapper;

import com.reactive.ms_tecnology.domain.model.Tecnology;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.entity.TecnologyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITecnologyMapper {

    Tecnology toDomain(TecnologyEntity entity);

    TecnologyEntity toEntity(Tecnology domain);
}