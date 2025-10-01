package com.reactive.ms_technology.infrastructure.input.rest;

import com.reactive.ms_technology.application.dto.request.TechnologyRequest;
import com.reactive.ms_technology.application.dto.response.PageResponse;
import com.reactive.ms_technology.application.dto.response.TechnologyResponse;
import com.reactive.ms_technology.application.handler.ITechnologyHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/v1/ms-technology/technology")
@RequiredArgsConstructor
@Validated
@Tag(name = "Technology", description = "Operations related to technology")
public class TechnologyRestController {

    private final ITechnologyHandler technologyHandler;

    @Operation(summary = "Create a new Technology", description = "Registers a new technology if it does not exist yet.")
    @ApiResponse(responseCode = "201", description = "Technology created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                    name = "Bad Request Example",
                    value = "{\"Message\": \"The request contains invalid data. Please check the submitted fields and try again\"}"
            )))
    @ApiResponse(responseCode = "409", description = "Conflict - Technology already exists",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(
                    name = "Conflict Example",
                    value = "{\"Message\": \"Technology with this name already exists\"}"
            )))
    @PostMapping
    public Mono<ResponseEntity<Void>> saveTechnology(@Valid @RequestBody TechnologyRequest request) {
        return technologyHandler.saveTechnology(request)
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @Operation(summary = "Get paginated technologies", description = "Returns a paginated list of registered technologies")
    @ApiResponse(responseCode = "200", description = "Paginated list of technologies",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    @GetMapping("/all")
    public Mono<PageResponse<TechnologyResponse>> findAllTechnologyPage(
            @Parameter(description = "Número de página", example = "0") @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Tamaño de página", example = "10") @RequestParam(defaultValue = "10") Integer size) {

        return technologyHandler.findAllTechnologyPage(page, size);
    }
}