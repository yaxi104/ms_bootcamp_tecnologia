package com.reactive.ms_tecnology.application.handler.impl;

import com.reactive.ms_tecnology.application.dto.request.TecnologyRequest;
import com.reactive.ms_tecnology.application.dto.response.PageResponse;
import com.reactive.ms_tecnology.application.dto.response.TecnologyResponse;
import com.reactive.ms_tecnology.application.handler.ITecnologyHandler;
import com.reactive.ms_tecnology.application.mapper.TecnologyRequestMapper;
import com.reactive.ms_tecnology.application.mapper.TecnologyResponseMapper;
import com.reactive.ms_tecnology.domain.api.ITecnologyServicePort;
import com.reactive.ms_tecnology.domain.model.PageInfo;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TecnologyHandler implements ITecnologyHandler {

    private final ITecnologyServicePort tecnologyServicePort;
    private final TecnologyRequestMapper tecnologyRequestMapper;
    private final TecnologyResponseMapper tecnologyResponseMapper;


    @Override
    public Mono<Void> saveTecnology(TecnologyRequest tecnologyRequest) {
        Tecnology domain = tecnologyRequestMapper.toDomain(tecnologyRequest);
        return tecnologyServicePort.saveTecnology(domain).then();
    }

    @Override
    public Mono<PageResponse<TecnologyResponse>> findAllTecnologyPage(Integer page, Integer size) {
        PageInfo pageInfo = PageInfo.of(page, size);

        return tecnologyServicePort.findAllTecnologyPage(pageInfo)
                .map(pageResult -> {
                    List<TecnologyResponse> content = pageResult.getContent().stream()
                            .map(tecnologyResponseMapper::toResponse)
                            .toList();

                    return new PageResponse<>(
                            content,
                            pageResult.getPage(),
                            pageResult.getSize(),
                            pageResult.getTotalElements(),
                            pageResult.getTotalPages()
                    );
                });
    }
}