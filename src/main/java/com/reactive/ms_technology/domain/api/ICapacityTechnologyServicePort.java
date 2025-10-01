package com.reactive.ms_technology.domain.api;

import com.reactive.ms_technology.domain.model.CapacityTechnology;
import com.reactive.ms_technology.domain.model.TechnologyCapacity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ICapacityTechnologyServicePort {

    Mono<Void> saveAllCapacityTechnology(Flux<CapacityTechnology> capacityTechnologyFlux);

    Flux<TechnologyCapacity> findTechnologiesByCapacityId(Long capacityId);

    Mono<Map<Long, List<TechnologyCapacity>>> findByCapacityIds(List<Long> capacityIds);
}
