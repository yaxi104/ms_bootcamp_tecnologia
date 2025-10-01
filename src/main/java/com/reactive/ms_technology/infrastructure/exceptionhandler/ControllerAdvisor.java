package com.reactive.ms_technology.infrastructure.exceptionhandler;

import com.reactive.ms_technology.domain.exception.BadRequestException;
import com.reactive.ms_technology.domain.exception.CapacityTecnologyAlreadyExistsException;
import com.reactive.ms_technology.domain.exception.TechnologyAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "Message";

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(
            BadRequestException badRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.BAD_REQUEST_MESSAGE.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap(MESSAGE, ExceptionResponse.BAD_REQUEST_MESSAGE.getMessage()));
    }

    @ExceptionHandler(TechnologyAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleTechnologyAlreadyExistsException(
            TechnologyAlreadyExistsException tecnologyAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.TECNOLOGY_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(CapacityTecnologyAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCapacityTecnologyAlreadyExistsException(
            CapacityTecnologyAlreadyExistsException capacityTecnologyAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CAPACITY_TECNOLOGY_ALREADY_EXISTS.getMessage()));
    }

}