package com.reactive.ms_technology.application.mapper;

import com.reactive.ms_technology.application.dto.request.CapacityTechnologyRequest;
import com.reactive.ms_technology.domain.model.CapacityTechnology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CapacityTechnologyRequestMapper {

    @Mapping(target = "id", ignore = true)
    CapacityTechnology toDomain(CapacityTechnologyRequest request);
}