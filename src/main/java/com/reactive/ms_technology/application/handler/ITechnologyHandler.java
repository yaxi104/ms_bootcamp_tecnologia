package com.reactive.ms_technology.application.handler;

import com.reactive.ms_technology.application.dto.request.TechnologyRequest;
import com.reactive.ms_technology.application.dto.response.PageResponse;
import com.reactive.ms_technology.application.dto.response.TechnologyResponse;
import reactor.core.publisher.Mono;

public interface ITechnologyHandler {

    Mono<Void> saveTechnology(TechnologyRequest tecnologyRequest);

    Mono<PageResponse<TechnologyResponse>> findAllTechnologyPage(Integer page, Integer size);

}
