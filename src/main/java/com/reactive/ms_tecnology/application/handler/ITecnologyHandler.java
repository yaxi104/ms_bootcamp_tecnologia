package com.reactive.ms_tecnology.application.handler;

import com.reactive.ms_tecnology.application.dto.request.TecnologyRequest;
import com.reactive.ms_tecnology.application.dto.response.PageResponse;
import com.reactive.ms_tecnology.application.dto.response.TecnologyResponse;
import reactor.core.publisher.Mono;

public interface ITecnologyHandler {

    Mono<Void> saveTecnology(TecnologyRequest tecnologyRequest);

    Mono<PageResponse<TecnologyResponse>> findAllTecnologyPage(Integer page, Integer size);

}
