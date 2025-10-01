package com.reactive.ms_technology.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta paginada")
public class PageResponse<T> {
    @Schema(description = "Contenido de la página")
    private List<T> content;

    @Schema(description = "Número de página actual", example = "0")
    private int page;

    @Schema(description = "Tamaño de página", example = "10")
    private int size;

    @Schema(description = "Total de elementos", example = "100")
    private long totalElements;

    @Schema(description = "Total de páginas", example = "10")
    private int totalPages;
}