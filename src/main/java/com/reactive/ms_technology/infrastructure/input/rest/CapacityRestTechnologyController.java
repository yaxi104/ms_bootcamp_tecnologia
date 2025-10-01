package com.reactive.ms_technology.infrastructure.input.rest;

import com.reactive.ms_technology.application.dto.request.CapacityTechnologyRequest;
import com.reactive.ms_technology.application.dto.response.TechnologyCapacityResponse;
import com.reactive.ms_technology.application.handler.ICapacityTechnologyHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/ms-technology/capacity")
@RequiredArgsConstructor
@Validated
@Tag(name = "Capacity-Technology", description = "Operations related to capacity-technology relationships")
public class CapacityRestTechnologyController {

    private final ICapacityTechnologyHandler capacityTechnologyHandler;

    @Operation(
            summary = "Create a Technology-Capacity relationship",
            description = "Registers a new association between a technology and a capacity if it does not already exist."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relationship created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(
                            name = "Bad Request Example",
                            value = "{\"message\": \"The request contains invalid data. Please check the submitted fields and try again.\"}"
                    ))
            ),
            @ApiResponse(responseCode = "409", description = "Conflict - Relationship already exists",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(
                            name = "Conflict Example",
                            value = "{\"message\": \"A relationship between this technology and capacity already exists.\"}"
                    ))
            )
    })
    @PostMapping("/technology/list")
    public Mono<ResponseEntity<Void>> saveCapacityTechnology(@Valid @RequestBody Flux<CapacityTechnologyRequest> request) {
        return capacityTechnologyHandler.saveAllCapacityTechnology(request)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @Operation(
            summary = "Get technologies by capacity ID",
            description = "Returns a list of technologies associated with the specified capacity ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of technologies",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TechnologyCapacityResponse.class)))
            ),
            @ApiResponse(responseCode = "404", description = "Capacity not found")
    })
    @GetMapping("/{capacityId}/technologies")
    public Flux<TechnologyCapacityResponse> findTechnologiesByCapacityId(
            @Parameter(description = "ID of the capacity", required = true, example = "123")
            @PathVariable("capacityId") Long capacityId
    ) {
        return capacityTechnologyHandler.findTechnologiesByCapacityId(capacityId);
    }
}
