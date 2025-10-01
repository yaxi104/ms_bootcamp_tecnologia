package com.reactive.ms_technology.domain.api;

import com.reactive.ms_technology.domain.model.PageInfo;
import com.reactive.ms_technology.domain.model.PageResult;
import com.reactive.ms_technology.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface ITechnologyServicePort {

    Mono<Technology> saveTechnology(Technology tecnology);

    Mono<PageResult<Technology>> findAllTechnologyPage(PageInfo pageInfo);

}
