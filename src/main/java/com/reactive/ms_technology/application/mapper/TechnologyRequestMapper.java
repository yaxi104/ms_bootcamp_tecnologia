package com.reactive.ms_technology.application.mapper;

import com.reactive.ms_technology.application.dto.request.TechnologyRequest;
import com.reactive.ms_technology.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TechnologyRequestMapper {

    @Mapping(target = "id", ignore = true)
    Technology toDomain(TechnologyRequest request);
}