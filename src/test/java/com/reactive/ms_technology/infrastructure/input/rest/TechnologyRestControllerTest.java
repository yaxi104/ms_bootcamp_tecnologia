package com.reactive.ms_technology.infrastructure.input.rest;

import com.reactive.ms_technology.application.dto.request.TechnologyRequest;
import com.reactive.ms_technology.application.dto.response.PageResponse;
import com.reactive.ms_technology.application.dto.response.TechnologyResponse;
import com.reactive.ms_technology.application.handler.ITechnologyHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = TechnologyRestController.class)
class TechnologyRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ITechnologyHandler technologyHandler;

    @Test
    void saveTechnologyShouldReturnCreated() {
        TechnologyRequest request = new TechnologyRequest();
        request.setName("Java");
        request.setDescription("A programming language");

        when(technologyHandler.saveTechnology(any(TechnologyRequest.class))).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/v1/ms-technology/technology")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();

        verify(technologyHandler).saveTechnology(any(TechnologyRequest.class));
    }

    @Test
    void findAllTechnologyPageShouldReturnPagedResults() {
        TechnologyResponse tech1 = new TechnologyResponse();
        tech1.setId(1L);
        tech1.setName("Java");

        PageResponse<TechnologyResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(List.of(tech1));
        pageResponse.setPage(0);
        pageResponse.setSize(10);
        pageResponse.setTotalElements(1L);

        when(technologyHandler.findAllTechnologyPage(0, 10)).thenReturn(Mono.just(pageResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/ms-technology/technology/all")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content[0].id").isEqualTo(1)
                .jsonPath("$.content[0].name").isEqualTo("Java")
                .jsonPath("$.page").isEqualTo(0)
                .jsonPath("$.size").isEqualTo(10)
                .jsonPath("$.totalElements").isEqualTo(1);

        verify(technologyHandler).findAllTechnologyPage(0, 10);
    }
}
