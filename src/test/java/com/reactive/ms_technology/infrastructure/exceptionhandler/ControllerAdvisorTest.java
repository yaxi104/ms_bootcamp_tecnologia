package com.reactive.ms_technology.infrastructure.exceptionhandler;

import com.reactive.ms_technology.domain.exception.BadRequestException;
import com.reactive.ms_technology.domain.exception.CapacityTecnologyAlreadyExistsException;
import com.reactive.ms_technology.domain.exception.TechnologyAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class ControllerAdvisorTest {
    private final ControllerAdvisor controllerAdvisor = new ControllerAdvisor();

    private static final String MESSAGE = "Message";

    @Test
    void handleBadRequestExceptionReturnsBadRequest() {
        BadRequestException ex = mock(BadRequestException.class);

        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleBadRequestException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ExceptionResponse.BAD_REQUEST_MESSAGE.getMessage(), response.getBody().get(MESSAGE));
    }

    @Test
    void handleMethodArgumentNotValidExceptionReturnsBadRequest() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);

        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ExceptionResponse.BAD_REQUEST_MESSAGE.getMessage(), response.getBody().get(MESSAGE));
    }

    @Test
    void handleTechnologyAlreadyExistsExceptionReturnsConflict() {
        TechnologyAlreadyExistsException ex = mock(TechnologyAlreadyExistsException.class);

        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleTechnologyAlreadyExistsException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ExceptionResponse.TECNOLOGY_ALREADY_EXISTS.getMessage(), response.getBody().get(MESSAGE));
    }

    @Test
    void handleCapacityTecnologyAlreadyExistsExceptionReturnsConflict() {
        CapacityTecnologyAlreadyExistsException ex = mock(CapacityTecnologyAlreadyExistsException.class);

        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleCapacityTecnologyAlreadyExistsException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ExceptionResponse.CAPACITY_TECNOLOGY_ALREADY_EXISTS.getMessage(), response.getBody().get(MESSAGE));
    }
}