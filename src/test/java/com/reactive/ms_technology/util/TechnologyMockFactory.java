package com.reactive.ms_technology.util;

import com.reactive.ms_technology.application.dto.request.TechnologyRequest;
import com.reactive.ms_technology.application.dto.response.TechnologyResponse;
import com.reactive.ms_technology.domain.model.Technology;
import com.reactive.ms_technology.infrastructure.output.r2dbc.technology.entity.TechnologyEntity;

import java.util.List;

public class TechnologyMockFactory {

    public static Technology createDefaultTecnology() {
        Technology tecnology = new Technology();
        tecnology.setId(1L);
        tecnology.setName("Java");
        tecnology.setDescription("Lenguaje OOP");
        return tecnology;
    }

    public static TechnologyEntity createDefaultTecnologyEntity() {
        TechnologyEntity tecnology = new TechnologyEntity();
        tecnology.setId(1L);
        tecnology.setName("Java");
        tecnology.setDescription("Lenguaje OOP");
        return tecnology;
    }

    public static TechnologyRequest createDefaultTecnologyRequest() {
        TechnologyRequest tecnology = new TechnologyRequest();
        tecnology.setName("Java");
        tecnology.setDescription("Lenguaje OOP");
        return tecnology;
    }

    public static List<Technology> createTecnologyList() {
        return List.of(
                createTecnology("Java", "Lenguaje OOP"),
                createTecnology("Python", "Lenguaje versátil"),
                createTecnology("Go", "Lenguaje eficiente para microservicios"),
                createTecnology("Rust", "Lenguaje seguro y rápido"),
                createTecnology("Kotlin", "Lenguaje moderno para Android")
        );
    }

    public static Technology createTecnology(String name, String description) {
        Technology tecnology = new Technology();
        tecnology.setName(name);
        tecnology.setDescription(description);
        return tecnology;
    }

    public static TechnologyResponse createTecnologyResponse(String name, String description) {
        TechnologyResponse response = new TechnologyResponse();
        response.setName(name);
        response.setDescription(description);
        return response;
    }

    public static List<TechnologyResponse> createTecnologyResponseList() {
        return List.of(
                createTecnologyResponse("Java", "Lenguaje OOP"),
                createTecnologyResponse("Python", "Lenguaje versátil"),
                createTecnologyResponse("Go", "Lenguaje eficiente para microservicios"),
                createTecnologyResponse("Rust", "Lenguaje seguro y rápido"),
                createTecnologyResponse("Kotlin", "Lenguaje moderno para Android")
        );
    }
}