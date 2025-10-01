package com.reactive.ms_technology.domain.spi;

import com.reactive.ms_technology.domain.model.PageInfo;
import com.reactive.ms_technology.domain.model.PageResult;
import com.reactive.ms_technology.domain.model.Technology;
import com.reactive.ms_technology.domain.model.TechnologyCapacity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITechnologyPersistencePort {

    Mono<Technology> save(Technology technology);

    Mono<PageResult<Technology>> findAll(PageInfo pageInfo);

    Mono<Boolean> existsByName(String name);

    Flux<TechnologyCapacity> findAlByTechnologyId(Flux<Long> technologyIdFlux);
}