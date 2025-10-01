package com.reactive.ms_technology.domain.spi;

import com.reactive.ms_technology.domain.model.CapacityTechnology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacityTechnologyPersistencePort {

    Mono<Void> saveAllCapacityTechnology(Flux<CapacityTechnology> capacityTechnologyFlux);

    Flux<CapacityTechnology> findByCapacityId(Long capacityId);

    Mono<CapacityTechnology> findByTechnologyIdAndCapacityId(CapacityTechnology capacityTechnology);
}