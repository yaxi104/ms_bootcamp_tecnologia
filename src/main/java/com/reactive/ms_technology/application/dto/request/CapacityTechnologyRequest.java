package com.reactive.ms_technology.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Entrada para crear una tecnología-capacidad")
public class CapacityTechnologyRequest {

    @NotNull
    @Schema(description = "Id de la tecnología", example = "1")
    private Long technologyId;

    @NotNull
    @Schema(description = "Id de la capacidad", example = "1")
    private Long capacityId;

}
