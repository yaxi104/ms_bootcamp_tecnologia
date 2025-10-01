package com.reactive.ms_technology.infrastructure.input.rest;
//
//import com.reactive.ms_technology.application.dto.request.TechnologyRequest;
//import com.reactive.ms_technology.application.dto.response.PageResponse;
//import com.reactive.ms_technology.application.dto.response.TechnologyResponse;
//import com.reactive.ms_technology.application.handler.ITechnologyHandler;
//import com.reactive.ms_technology.util.TechnologyMockFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class TechnologyRestControllerTest {
//
//    private WebTestClient webTestClient;
//    private ITechnologyHandler tecnologyHandler;
//
//    @BeforeEach
//    void setUp() {
//        tecnologyHandler = Mockito.mock(ITechnologyHandler.class);
//        TechnologyRestController controller = new TechnologyRestController(tecnologyHandler);
//
//        webTestClient = WebTestClient.bindToController(controller).build();
//    }
//
//    @Test
//    void saveTecnologySuccessTest() {
//        TechnologyRequest request = TechnologyMockFactory.createDefaultTecnologyRequest();
//
//        Mockito.when(tecnologyHandler.saveTechnology(Mockito.any())).thenReturn(Mono.empty());
//
//        webTestClient.post()
//                .uri("/api/v1/technology")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(request)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody().isEmpty();
//    }
//
//    @Test
//    void findAllTecnologyPageSuccessTest() {
//        int page = 0;
//        int size = 5;
//
//        List<TechnologyResponse> responses = TechnologyMockFactory.createTecnologyResponseList();
//        PageResponse<TechnologyResponse> pageResponse = new PageResponse<>(responses, page, size, 10, 2);
//
//        Mockito.when(tecnologyHandler.findAllTechnologyPage(page, size)).thenReturn(Mono.just(pageResponse));
//
//        webTestClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/api/v1/technology/all")
//                        .queryParam("page", page)
//                        .queryParam("size", size)
//                        .build())
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(PageResponse.class)
//                .value(response -> {
//                    assertEquals(5, ((PageResponse<?>) response).getContent().size());
//                    assertEquals(10, ((PageResponse<?>) response).getTotalElements());
//                    assertEquals(2, ((PageResponse<?>) response).getTotalPages());
//                });
//    }
//}