package com.reactive.ms_tecnology.domain.api;

import com.reactive.ms_tecnology.domain.model.PageInfo;
import com.reactive.ms_tecnology.domain.model.PageResult;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import reactor.core.publisher.Mono;

public interface ITecnologyServicePort {

    Mono<Tecnology> saveTecnology(Tecnology tecnology);

    Mono<PageResult<Tecnology>> findAllTecnologyPage(PageInfo pageInfo);

}
