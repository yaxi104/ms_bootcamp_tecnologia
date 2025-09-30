package com.reactive.ms_tecnology.util;

import com.reactive.ms_tecnology.application.dto.request.TecnologyRequest;
import com.reactive.ms_tecnology.application.dto.response.TecnologyResponse;
import com.reactive.ms_tecnology.domain.model.Tecnology;
import com.reactive.ms_tecnology.infrastructure.output.jpa.tecnology.entity.TecnologyEntity;

import java.util.List;

public class TecnologyMockFactory {

    public static Tecnology createDefaultTecnology() {
        Tecnology tecnology = new Tecnology();
        tecnology.setId(1L);
        tecnology.setName("Java");
        tecnology.setDescription("Lenguaje OOP");
        return tecnology;
    }

    public static TecnologyEntity createDefaultTecnologyEntity() {
        TecnologyEntity tecnology = new TecnologyEntity();
        tecnology.setId(1L);
        tecnology.setName("Java");
        tecnology.setDescription("Lenguaje OOP");
        return tecnology;
    }

    public static TecnologyRequest createDefaultTecnologyRequest() {
        TecnologyRequest tecnology = new TecnologyRequest();
        tecnology.setName("Java");
        tecnology.setDescription("Lenguaje OOP");
        return tecnology;
    }

    public static List<Tecnology> createTecnologyList() {
        return List.of(
                createTecnology("Java", "Lenguaje OOP"),
                createTecnology("Python", "Lenguaje versátil"),
                createTecnology("Go", "Lenguaje eficiente para microservicios"),
                createTecnology("Rust", "Lenguaje seguro y rápido"),
                createTecnology("Kotlin", "Lenguaje moderno para Android")
        );
    }

    public static Tecnology createTecnology(String name, String description) {
        Tecnology tecnology = new Tecnology();
        tecnology.setName(name);
        tecnology.setDescription(description);
        return tecnology;
    }

    public static TecnologyResponse createTecnologyResponse(String name, String description) {
        TecnologyResponse response = new TecnologyResponse();
        response.setName(name);
        response.setDescription(description);
        return response;
    }

    public static List<TecnologyResponse> createTecnologyResponseList() {
        return List.of(
                createTecnologyResponse("Java", "Lenguaje OOP"),
                createTecnologyResponse("Python", "Lenguaje versátil"),
                createTecnologyResponse("Go", "Lenguaje eficiente para microservicios"),
                createTecnologyResponse("Rust", "Lenguaje seguro y rápido"),
                createTecnologyResponse("Kotlin", "Lenguaje moderno para Android")
        );
    }
}