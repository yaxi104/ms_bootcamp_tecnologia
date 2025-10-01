package com.reactive.ms_technology.domain.utils;

import com.reactive.ms_technology.domain.exception.BadRequestException;
import reactor.core.publisher.Mono;

public class ValidateRequest {

    private ValidateRequest() {
    }

    public static Mono<Void> checkNotBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Mono.error(new BadRequestException());
        }
        return Mono.empty();
    }

    public static Mono<Void> checkNotLengthValid(String value, int lengthMax) {
        if (value.length() > lengthMax) {
            return Mono.error(new BadRequestException());
        }
        return Mono.empty();
    }

    public static Mono<Void> checkId(Long value) {
        if (value == null || value <= 0) {
            return Mono.error(new BadRequestException());
        }
        return Mono.empty();
    }
}