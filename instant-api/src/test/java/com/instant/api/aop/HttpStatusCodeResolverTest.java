package com.instant.api.aop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpStatusCodeResolverTest {

    private final HttpStatusCodeResolver resolver = new HttpStatusCodeResolver();

    @Test
    void shouldResolveNull() {
        assertEquals(-1, resolver.resolve(null));
        assertEquals(-1, resolver.resolve(new Object()));
    }

    @ParameterizedTest
    @EnumSource(HttpStatus.class)
    void shouldResolveHttpStatus(HttpStatus httpStatus) {
        assertEquals(codeOf(httpStatus), resolver.resolve(httpStatus));
    }

    @ParameterizedTest
    @EnumSource(HttpStatus.class)
    void shouldResolveResponseEntity(HttpStatus httpStatus) {
        assertEquals(codeOf(httpStatus), resolver.resolve(ResponseEntity.status(httpStatus).build()));
    }

    private static int codeOf(HttpStatus httpStatus) {
        return HttpStatus.valueOf(httpStatus.value()).value();
    }
}