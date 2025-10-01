package com.reactive.ms_technology.application.handler.impl;

import com.reactive.ms_technology.application.dto.request.CapacityTechnologyRequest;
import com.reactive.ms_technology.application.dto.response.TechnologyCapacityResponse;
import com.reactive.ms_technology.application.mapper.CapacityTechnologyRequestMapper;
import com.reactive.ms_technology.application.mapper.CapacityTechnologyResponseMapper;
import com.reactive.ms_technology.domain.api.ICapacityTechnologyServicePort;
import com.reactive.ms_technology.domain.model.CapacityTechnology;
import com.reactive.ms_technology.domain.model.TechnologyCapacity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CapacityTechnologyHandlerTest {

    @Mock
    private ICapacityTechnologyServicePort servicePort;

    @Mock
    private CapacityTechnologyRequestMapper requestMapper;

    @Mock
    private CapacityTechnologyResponseMapper responseMapper;

    @InjectMocks
    private CapacityTechnologyHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAllCapacityTechnologyTest() {
        CapacityTechnologyRequest req1 = new CapacityTechnologyRequest();
        req1.setTechnologyId(101L);
        req1.setCapacityId(1L);

        CapacityTechnology domain1 = new CapacityTechnology(null, 1L, 101L);

        when(requestMapper.toDomain(any())).thenReturn(domain1);

        ArgumentCaptor<Flux<CapacityTechnology>> captor = ArgumentCaptor.forClass(Flux.class);
        when(servicePort.saveAllCapacityTechnology(captor.capture())).thenReturn(Mono.empty());

        Mono<Void> result = handler.saveAllCapacityTechnology(Flux.just(req1));

        StepVerifier.create(result)
                .verifyComplete();

        captor.getValue().blockLast();

        verify(requestMapper).toDomain(req1);
        verify(servicePort).saveAllCapacityTechnology(any(Flux.class));
    }

    @Test
    void findTechnologiesByCapacityIdTest() {
        Long capacityId = 1L;

        TechnologyCapacity domain = new TechnologyCapacity(1L, "Java");

        TechnologyCapacityResponse response = new TechnologyCapacityResponse();
        response.setId(1L);
        response.setName("Java");

        when(servicePort.findTechnologiesByCapacityId(capacityId)).thenReturn(Flux.just(domain));
        when(responseMapper.toResponse(domain)).thenReturn(response);

        Flux<TechnologyCapacityResponse> result = handler.findTechnologiesByCapacityId(capacityId);

        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();

        verify(servicePort).findTechnologiesByCapacityId(capacityId);
        verify(responseMapper).toResponse(domain);
    }
}