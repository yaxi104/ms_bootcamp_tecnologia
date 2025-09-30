package com.reactive.ms_tecnology.application.handler.impl;

import com.reactive.ms_tecnology.application.dto.request.TecnologyRequest;
import com.reactive.ms_tecnology.application.dto.response.TecnologyResponse;
import com.reactive.ms_tecnology.application.mapper.TecnologyRequestMapper;
import com.reactive.ms_tecnology.application.mapper.TecnologyResponseMapper;
import com.reactive.ms_tecnology.domain.api.ITecnologyServicePort;
import com.reactive.ms_tecnology.domain.model.PageResult;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import com.reactive.ms_tecnology.util.TecnologyMockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TecnologyHandlerTest {

    private ITecnologyServicePort tecnologyServicePort;
    private TecnologyRequestMapper tecnologyRequestMapper;
    private TecnologyResponseMapper tecnologyResponseMapper;
    private TecnologyHandler tecnologyHandler;

    @BeforeEach
    void setUp() {
        tecnologyServicePort = Mockito.mock(ITecnologyServicePort.class);
        tecnologyRequestMapper = Mockito.mock(TecnologyRequestMapper.class);
        tecnologyResponseMapper = Mockito.mock(TecnologyResponseMapper.class);
        tecnologyHandler = new TecnologyHandler(tecnologyServicePort, tecnologyRequestMapper, tecnologyResponseMapper);
    }

    @Test
    void saveTecnologySuccessTest() {
        TecnologyRequest request = TecnologyMockFactory.createDefaultTecnologyRequest();
        Tecnology domain = TecnologyMockFactory.createDefaultTecnology();

        Mockito.when(tecnologyRequestMapper.toDomain(request)).thenReturn(domain);
        Mockito.when(tecnologyServicePort.saveTecnology(domain)).thenReturn(Mono.just(domain));

        StepVerifier.create(tecnologyHandler.saveTecnology(request))
                .verifyComplete();

        Mockito.verify(tecnologyRequestMapper).toDomain(request);
        Mockito.verify(tecnologyServicePort).saveTecnology(domain);
    }

    @Test
    void findAllTecnologyPageSuccessTest() {
        int page = 0;
        int size = 3;

        List<Tecnology> domainList = TecnologyMockFactory.createTecnologyList();
        PageResult<Tecnology> pageResult = new PageResult<>(domainList, page, size, 10);

        Mockito.when(tecnologyServicePort.findAllTecnologyPage(Mockito.any())).thenReturn(Mono.just(pageResult));

        for (Tecnology tecnology : domainList) {
            TecnologyResponse response = TecnologyMockFactory.createTecnologyResponse(tecnology.getName(), tecnology.getDescription());
            Mockito.when(tecnologyResponseMapper.toResponse(tecnology)).thenReturn(response);
        }

        StepVerifier.create(tecnologyHandler.findAllTecnologyPage(page, size))
                .assertNext(response -> {
                    List<String> actualNames = response.getContent().stream()
                            .map(TecnologyResponse::getName)
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