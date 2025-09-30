package com.reactive.ms_tecnology.infrastructure.input.rest;

import com.reactive.ms_tecnology.application.dto.request.TecnologyRequest;
import com.reactive.ms_tecnology.application.dto.response.PageResponse;
import com.reactive.ms_tecnology.application.dto.response.TecnologyResponse;
import com.reactive.ms_tecnology.application.handler.ITecnologyHandler;
import com.reactive.ms_tecnology.util.TecnologyMockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TecnologyRestControllerTest {

    private WebTestClient webTestClient;
    private ITecnologyHandler tecnologyHandler;

    @BeforeEach
    void setUp() {
        tecnologyHandler = Mockito.mock(ITecnologyHandler.class);
        TecnologyRestController controller = new TecnologyRestController(tecnologyHandler);

        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void saveTecnologySuccessTest() {
        TecnologyRequest request = TecnologyMockFactory.createDefaultTecnologyRequest();

        Mockito.when(tecnologyHandler.saveTecnology(Mockito.any())).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/v1/tecnology")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    void findAllTecnologyPageSuccessTest() {
        int page = 0;
        int size = 5;

        List<TecnologyResponse> responses = TecnologyMockFactory.createTecnologyResponseList();
        PageResponse<TecnologyResponse> pageResponse = new PageResponse<>(responses, page, size, 10, 2);

        Mockito.when(tecnologyHandler.findAllTecnologyPage(page, size)).thenReturn(Mono.just(pageResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/tecnology/all")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageResponse.class)
                .value(response -> {
                    assertEquals(5, ((PageResponse<?>) response).getContent().size());
                    assertEquals(10, ((PageResponse<?>) response).getTotalElements());
                    assertEquals(2, ((PageResponse<?>) response).getTotalPages());
                });
    }
}