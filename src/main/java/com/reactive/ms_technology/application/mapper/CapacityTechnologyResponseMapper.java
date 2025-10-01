package com.reactive.ms_technology.application.mapper;

import com.reactive.ms_technology.application.dto.response.TechnologyCapacityResponse;
import com.reactive.ms_technology.domain.model.TechnologyCapacity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CapacityTechnologyResponseMapper {

    TechnologyCapacityResponse toResponse(TechnologyCapacity domain);
}