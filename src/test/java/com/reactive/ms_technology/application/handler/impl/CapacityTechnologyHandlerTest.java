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

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void findByCapacityIdsTest() {
        Long id1 = 1L;
        Long id2 = 2L;

        TechnologyCapacity tech1 = new TechnologyCapacity(101L, "Java");
        TechnologyCapacity tech2 = new TechnologyCapacity(102L, "Python");

        TechnologyCapacity tech3 = new TechnologyCapacity(201L, "Go");

        TechnologyCapacityResponse resp1 = new TechnologyCapacityResponse();
        resp1.setId(101L);
        resp1.setName("Java");

        TechnologyCapacityResponse resp2 = new TechnologyCapacityResponse();
        resp2.setId(102L);
        resp2.setName("Python");

        TechnologyCapacityResponse resp3 = new TechnologyCapacityResponse();
        resp3.setId(201L);
        resp3.setName("Go");

        when(servicePort.findTechnologiesByCapacityId(id1)).thenReturn(Flux.just(tech1, tech2));
        when(servicePort.findTechnologiesByCapacityId(id2)).thenReturn(Flux.just(tech3));

        when(responseMapper.toResponse(tech1)).thenReturn(resp1);
        when(responseMapper.toResponse(tech2)).thenReturn(resp2);
        when(responseMapper.toResponse(tech3)).thenReturn(resp3);

        Mono<Map<Long, List<TechnologyCapacityResponse>>> result = handler.findByCapacityIds(List.of(id1, id2));

        StepVerifier.create(result)
                .assertNext(map -> {
                    assertEquals(2, map.size());
                    assertEquals(List.of(resp1, resp2), map.get(id1));
                    assertEquals(List.of(resp3), map.get(id2));
                })
                .verifyComplete();

        verify(servicePort).findTechnologiesByCapacityId(id1);
        verify(servicePort).findTechnologiesByCapacityId(id2);
        verify(responseMapper).toResponse(tech1);
        verify(responseMapper).toResponse(tech2);
        verify(responseMapper).toResponse(tech3);
    }
}
