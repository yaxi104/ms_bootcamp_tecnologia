package com.reactive.ms_tecnology.domain.spi;

import com.reactive.ms_tecnology.domain.model.PageInfo;
import com.reactive.ms_tecnology.domain.model.PageResult;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import reactor.core.publisher.Mono;

public interface ITecnologyPersistencePort {

    Mono<Tecnology> save(Tecnology tecnology);

    Mono<PageResult<Tecnology>> findAll(PageInfo pageInfo);

    Mono<Boolean> existsByName(String name);

}