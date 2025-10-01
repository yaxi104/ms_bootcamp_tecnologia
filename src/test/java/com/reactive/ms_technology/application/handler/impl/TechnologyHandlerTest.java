package com.reactive.ms_technology.application.handler.impl;

import com.reactive.ms_technology.application.dto.request.TechnologyRequest;
import com.reactive.ms_technology.application.dto.response.TechnologyResponse;
import com.reactive.ms_technology.application.mapper.TechnologyRequestMapper;
import com.reactive.ms_technology.application.mapper.TechnologyResponseMapper;
import com.reactive.ms_technology.domain.api.ITechnologyServicePort;
import com.reactive.ms_technology.domain.model.PageResult;
import com.reactive.ms_technology.domain.model.Technology;
import com.reactive.ms_technology.util.TechnologyMockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TechnologyHandlerTest {

    private ITechnologyServicePort technologyServicePort;
    private TechnologyRequestMapper technologyRequestMapper;
    private TechnologyResponseMapper technologyResponseMapper;
    private TechnologyHandler technologyHandler;

    @BeforeEach
    void setUp() {
        technologyServicePort = Mockito.mock(ITechnologyServicePort.class);
        technologyRequestMapper = Mockito.mock(TechnologyRequestMapper.class);
        technologyResponseMapper = Mockito.mock(TechnologyResponseMapper.class);
        technologyHandler = new TechnologyHandler(technologyServicePort, technologyRequestMapper, technologyResponseMapper);
    }

    @Test
    void saveTechnologySuccessTest() {
        TechnologyRequest request = TechnologyMockFactory.createDefaultTecnologyRequest();
        Technology domain = TechnologyMockFactory.createDefaultTecnology();

        Mockito.when(technologyRequestMapper.toDomain(request)).thenReturn(domain);
        Mockito.when(technologyServicePort.saveTechnology(domain)).thenReturn(Mono.just(domain));

        StepVerifier.create(technologyHandler.saveTechnology(request))
                .verifyComplete();

        Mockito.verify(technologyRequestMapper).toDomain(request);
        Mockito.verify(technologyServicePort).saveTechnology(domain);
    }

    @Test
    void findAllTecnologyPageSuccessTest() {
        int page = 0;
        int size = 3;

        List<Technology> domainList = TechnologyMockFactory.createTecnologyList();
        PageResult<Technology> pageResult = new PageResult<>(domainList, page, size, 10);

        Mockito.when(technologyServicePort.findAllTechnologyPage(Mockito.any())).thenReturn(Mono.just(pageResult));

        for (Technology tecnology : domainList) {
            TechnologyResponse response = TechnologyMockFactory.createTecnologyResponse(tecnology.getName(), tecnology.getDescription());
            Mockito.when(technologyResponseMapper.toResponse(tecnology)).thenReturn(response);
        }

        StepVerifier.create(technologyHandler.findAllTechnologyPage(page, size))
                .assertNext(response -> {
                    List<String> actualNames = response.getContent().stream()
                            .map(TechnologyResponse::getName)
                            .toList();

                    List<String> expectedNames = List.of("Java", "Python", "Go", "Rust", "Kotlin");

                    assertTrue(actualNames.containsAll(expectedNames));
                    assertEquals(page, response.getPage());
                    assertEquals(size, response.getSize());
                    assertEquals(10, response.getTotalElements());
                    assertEquals(4, response.getTotalPages());
                })
                .verifyComplete();
    }
}