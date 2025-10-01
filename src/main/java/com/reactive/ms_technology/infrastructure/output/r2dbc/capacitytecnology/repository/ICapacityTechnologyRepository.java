package com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.repository;

import com.reactive.ms_technology.infrastructure.output.r2dbc.capacitytecnology.entity.CapacityTechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacityTechnologyRepository extends ReactiveCrudRepository<CapacityTechnologyEntity, Long> {

    Mono<CapacityTechnologyEntity> findByTechnologyIdAndCapacityId(Long technologyId, Long capacityId);

    Flux<CapacityTechnologyEntity> findByCapacityId(Long capacityId);
}