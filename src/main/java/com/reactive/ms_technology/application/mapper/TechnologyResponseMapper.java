package com.reactive.ms_technology.application.mapper;

import com.reactive.ms_technology.application.dto.response.TechnologyResponse;
import com.reactive.ms_technology.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TechnologyResponseMapper {

    TechnologyResponse toResponse(Technology domain);
}