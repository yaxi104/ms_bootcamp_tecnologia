package com.reactive.ms_technology.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Entrada para crear una tecnología")
public class TechnologyRequest {

    @NotBlank
    @Size(max = 50)
    @Schema(description = "Nombre de la tecnología", example = "Java")
    private String name;

    @NotBlank
    @Size(max = 90)
    @Schema(description = "Descripción de la tecnología", example = "Lenguaje de programación orientado a objetos")
    private String description;
}
