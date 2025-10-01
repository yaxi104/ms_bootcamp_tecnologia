package com.reactive.ms_technology.domain.utils;

import com.reactive.ms_technology.domain.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import reactor.test.StepVerifier;

class ValidateRequestTest {

    @Test
    void checkNotBlankSuccessTest() {
        StepVerifier.create(ValidateRequest.checkNotBlank("Java"))
                .verifyComplete();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void checkNotBlankFailTest(String arg) {
        StepVerifier.create(ValidateRequest.checkNotBlank(arg))
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    void checkNotLengthValidSuccessTest() {
        StepVerifier.create(ValidateRequest.checkNotLengthValid("Java", 10))
                .verifyComplete();
    }

    @Test
    void checkNotLengthValidFailTest() {
        StepVerifier.create(ValidateRequest.checkNotLengthValid("Lenguaje muy largo", 5))
                .expectError(BadRequestException.class)
                .verify();
    }
}