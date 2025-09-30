package com.reactive.ms_tecnology.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Respuesta con los datos de una tecnología registrada")
public class TecnologyResponse {

    @Schema(description = "Identificador único de la tecnología", example = "1")
    private Long id;

    @Schema(description = "Nombre de la tecnología", example = "Java")
    private String name;

    @Schema(description = "Descripción de la tecnología", example = "Lenguaje de programación orientado a objetos")
    private String description;

}