package com.reactive.ms_tecnology.infrastructure.input.rest;

import com.reactive.ms_tecnology.application.dto.request.TecnologyRequest;
import com.reactive.ms_tecnology.application.dto.response.PageResponse;
import com.reactive.ms_tecnology.application.dto.response.TecnologyResponse;
import com.reactive.ms_tecnology.application.handler.ITecnologyHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/tecnology")
@RequiredArgsConstructor
@Validated
@Tag(name = "Tecnology", description = "Operations related to tecnology")
public class TecnologyRestController {

    private final ITecnologyHandler tecnologyHandler;

    @Operation(
            summary = "Create a new Technology",
            description = "Registers a new technology if it does not exist yet."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Technology created successfully",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Bad Request Example",
                                    value = "{\"Message\": \"The request contains invalid data. Please check the submitted fields and try again\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Technology already exists",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Conflict Example",
                                    value = "{\"Message\": \"Technology with this name already exists\"}"
                            )
                    )
            )
    })
    @PostMapping
    public Mono<ResponseEntity<Void>> saveTecnology(@Valid @RequestBody TecnologyRequest request) {
        return tecnologyHandler.saveTecnology(request)
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @Operation(
            summary = "Obtener tecnologías paginadas",
            description = "Devuelve una lista paginada de tecnologías registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista paginada de tecnologías",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class)
                    )
            )
    })
    @GetMapping("/all")
    public Mono<PageResponse<TecnologyResponse>> findAllTecnologyPage(
            @Parameter(description = "Número de página", example = "0")
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        return tecnologyHandler.findAllTecnologyPage(page, size);
    }

}
