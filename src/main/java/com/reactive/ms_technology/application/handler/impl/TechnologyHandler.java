package com.reactive.ms_technology.application.handler.impl;

import com.reactive.ms_technology.application.dto.request.TechnologyRequest;
import com.reactive.ms_technology.application.dto.response.PageResponse;
import com.reactive.ms_technology.application.dto.response.TechnologyResponse;
import com.reactive.ms_technology.application.handler.ITechnologyHandler;
import com.reactive.ms_technology.application.mapper.TechnologyRequestMapper;
import com.reactive.ms_technology.application.mapper.TechnologyResponseMapper;
import com.reactive.ms_technology.domain.api.ITechnologyServicePort;
import com.reactive.ms_technology.domain.model.PageInfo;
import com.reactive.ms_technology.domain.model.Technology;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyHandler implements ITechnologyHandler {

    private final ITechnologyServicePort technologyServicePort;
    private final TechnologyRequestMapper technologyRequestMapper;
    private final TechnologyResponseMapper technologyResponseMapper;

    @Override
    public Mono<Void> saveTechnology(TechnologyRequest request) {
        Technology domain = technologyRequestMapper.toDomain(request);
        return technologyServicePort.saveTechnology(domain).then();
    }

    @Override
    public Mono<PageResponse<TechnologyResponse>> findAllTechnologyPage(Integer page, Integer size) {
        PageInfo pageInfo = PageInfo.of(page, size);

        return technologyServicePort.findAllTechnologyPage(pageInfo)
                .map(pageResult -> {
                    List<TechnologyResponse> content = pageResult.getContent().stream()
                            .map(technologyResponseMapper::toResponse)
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