package com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.repository;

import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.entity.TecnologyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITecnologyRepository extends ReactiveCrudRepository<TecnologyEntity, Long> {

    @Query("SELECT * FROM tecnologia ORDER BY nombre ASC LIMIT :limit OFFSET :offset")
    Flux<TecnologyEntity> findAllOrderedByName(int limit, int offset);

    @Query("SELECT COUNT(*) FROM tecnologia")
    Mono<Long> countAll();

    Mono<Boolean> existsByName(String name);
}
