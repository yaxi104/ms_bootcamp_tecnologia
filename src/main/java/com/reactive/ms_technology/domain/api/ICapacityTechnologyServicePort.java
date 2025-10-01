package com.reactive.ms_technology.domain.api;

import com.reactive.ms_technology.domain.model.CapacityTechnology;
import com.reactive.ms_technology.domain.model.TechnologyCapacity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacityTechnologyServicePort {

    Mono<Void> saveAllCapacityTechnology(Flux<CapacityTechnology> capacityTechnologyFlux);

    Flux<TechnologyCapacity> findTechnologiesByCapacityId(Long capacityId);

}
