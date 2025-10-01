package com.reactive.ms_technology.infrastructure.output.r2dbc.technology.repository;

import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {

    @Query("SELECT * FROM tecnologia ORDER BY nombre ASC LIMIT :limit OFFSET :offset")
    Flux<TechnologyEntity> findAllOrderedByName(int limit, int offset);

    @Query("SELECT COUNT(*) FROM tecnologia")
    Mono<Long> countAll();

    Mono<Boolean> existsByName(String name);

}
