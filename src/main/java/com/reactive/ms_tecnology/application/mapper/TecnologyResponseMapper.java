package com.reactive.ms_tecnology.application.mapper;

import com.reactive.ms_tecnology.application.dto.response.TecnologyResponse;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TecnologyResponseMapper {

    TecnologyResponse toResponse(Tecnology domain);
}