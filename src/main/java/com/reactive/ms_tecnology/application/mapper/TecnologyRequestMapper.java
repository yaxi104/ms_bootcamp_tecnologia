package com.reactive.ms_tecnology.application.mapper;

import com.reactive.ms_tecnology.application.dto.request.TecnologyRequest;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TecnologyRequestMapper {

    @Mapping(target = "id", ignore = true)
    Tecnology toDomain(TecnologyRequest request);
}