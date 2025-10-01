package com.reactive.ms_technology.infrastructure.input.rest;

import com.reactive.ms_technology.application.dto.request.CapacityTechnologyRequest;
import com.reactive.ms_technology.application.dto.response.TechnologyCapacityResponse;
import com.reactive.ms_technology.application.handler.ICapacityTechnologyHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CapacityRestTechnologyController.class)
class CapacityRestTechnologyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ICapacityTechnologyHandler capacityTechnologyHandler;

    @Test
    void saveCapacityTechnologyShouldReturnCreated() {
        CapacityTechnologyRequest request1 = new CapacityTechnologyRequest();
        request1.setCapacityId(1L);
        request1.setTechnologyId(101L);

        Flux<CapacityTechnologyRequest> requestFlux = Flux.just(request1);

        when(capacityTechnologyHandler.saveAllCapacityTechnology(any(Flux.class))).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/v1/ms-technology/capacity/technology/list")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestFlux, CapacityTechnologyRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();

        verify(capacityTechnologyHandler).saveAllCapacityTechnology(any(Flux.class));
    }

    @Test
    void findTechnologiesByCapacityIdShouldReturnTechnologyList() {
        Long capacityId = 123L;

        TechnologyCapacityResponse techResponse = new TechnologyCapacityResponse();
        techResponse.setId(1L);
        techResponse.setName("Java");

        when(capacityTechnologyHandler.findTechnologiesByCapacityId(capacityId))
                .thenReturn(Flux.just(techResponse));

        webTestClient.get()
                .uri("/api/v1/ms-technology/capacity/{capacityId}/technologies", capacityId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TechnologyCapacityResponse.class)
                .hasSize(1)
                .value(responses -> {
                    TechnologyCapacityResponse response = responses.get(0);
                    assertEquals(techResponse.getId(), response.getId());
                    assertEquals(techResponse.getName(), response.getName());
                });

        verify(capacityTechnologyHandler).findTechnologiesByCapacityId(capacityId);
    }

    @Test
    void findTechnologiesByCapacityIdsShouldReturnMap() {
        Long id1 = 1L;
        Long id2 = 2L;

        TechnologyCapacityResponse tech1 = new TechnologyCapacityResponse();
        tech1.setId(101L);
        tech1.setName("Java");

        TechnologyCapacityResponse tech2 = new TechnologyCapacityResponse();
        tech2.setId(102L);
        tech2.setName("Python");

        TechnologyCapacityResponse tech3 = new TechnologyCapacityResponse();
        tech3.setId(201L);
        tech3.setName("Go");

        Map<Long, List<TechnologyCapacityResponse>> responseMap = Map.of(
                id1, List.of(tech1, tech2),
                id2, List.of(tech3)
        );

        when(capacityTechnologyHandler.findByCapacityIds(List.of(id1, id2)))
                .thenReturn(Mono.just(responseMap));

        webTestClient.post()
                .uri("/api/v1/ms-technology/capacity/by-capacity-ids")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(id1, id2))
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Map<Long, List<TechnologyCapacityResponse>>>() {
                })
                .value(map -> {
                    assertEquals(2, map.size());
                    assertEquals(2, map.get(id1).size());
                    assertEquals("Java", map.get(id1).get(0).getName());
                    assertEquals("Python", map.get(id1).get(1).getName());
                    assertEquals(1, map.get(id2).size());
                    assertEquals("Go", map.get(id2).get(0).getName());
                });

        verify(capacityTechnologyHandler).findByCapacityIds(List.of(id1, id2));
    }

}