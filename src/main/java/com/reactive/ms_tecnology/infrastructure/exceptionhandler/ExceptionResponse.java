package com.reactive.ms_tecnology.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    TECNOLOGY_ALREADY_EXISTS("Technology with this name already exists"),
    BAD_REQUEST_MESSAGE("The request contains invalid data. Please check the submitted fields and try again");

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}