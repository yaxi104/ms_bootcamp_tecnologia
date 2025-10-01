package com.reactive.ms_technology.application.handler;

import com.reactive.ms_technology.application.dto.request.CapacityTechnologyRequest;
import com.reactive.ms_technology.application.dto.response.TechnologyCapacityResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacityTechnologyHandler {

    Mono<Void> saveAllCapacityTechnology(Flux<CapacityTechnologyRequest> requestFlux);

    Flux<TechnologyCapacityResponse> findTechnologiesByCapacityId(Long capacityId);

}
