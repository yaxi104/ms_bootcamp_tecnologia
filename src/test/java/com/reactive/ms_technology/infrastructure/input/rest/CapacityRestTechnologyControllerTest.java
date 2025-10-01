package com.reactive.ms_technology.infrastructure.input.rest;

import com.reactive.ms_technology.application.dto.request.CapacityTechnologyRequest;
import com.reactive.ms_technology.application.dto.response.TechnologyCapacityResponse;
import com.reactive.ms_technology.application.handler.ICapacityTechnologyHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
}