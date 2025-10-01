package com.reactive.ms_technology.application.handler.impl;

import com.reactive.ms_technology.application.dto.request.CapacityTechnologyRequest;
import com.reactive.ms_technology.application.dto.response.TechnologyCapacityResponse;
import com.reactive.ms_technology.application.handler.ICapacityTechnologyHandler;
import com.reactive.ms_technology.application.mapper.CapacityTechnologyRequestMapper;
import com.reactive.ms_technology.application.mapper.CapacityTechnologyResponseMapper;
import com.reactive.ms_technology.domain.api.ICapacityTechnologyServicePort;
import com.reactive.ms_technology.domain.model.CapacityTechnology;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CapacityTechnologyHandler implements ICapacityTechnologyHandler {

    private final ICapacityTechnologyServicePort capacityTechnologyServicePort;
    private final CapacityTechnologyRequestMapper capacityTechnologyRequestMapper;
    private final CapacityTechnologyResponseMapper capacityTechnologyResponseMapper;

    @Override
    public Mono<Void> saveAllCapacityTechnology(Flux<CapacityTechnologyRequest> requestFlux) {
        Flux<CapacityTechnology> domainFlux = requestFlux.map(capacityTechnologyRequestMapper::toDomain);
        return capacityTechnologyServicePort.saveAllCapacityTechnology(domainFlux).then();
    }

    @Override
    public Flux<TechnologyCapacityResponse> findTechnologiesByCapacityId(Long capacityId) {
        return capacityTechnologyServicePort.findTechnologiesByCapacityId(capacityId)
                .map(capacityTechnologyResponseMapper::toResponse);
    }
}