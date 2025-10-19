package com.instant.fetcher.exception;

import lombok.Getter;

import org.springframework.http.HttpStatusCode;

import java.io.Serial;

/**
 * Exception thrown when an error occurs during a service call.
 */
@Getter
public class ServiceCallException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4277228266705610029L;

    private final HttpStatusCode httpStatusCode;

    /**
     * Constructs a new instance.
     *
     * @param message the exception message
     * @param cause the root cause
     */
    public ServiceCallException(String message, Throwable cause) {
        this(null, message, cause);
    }

    /**
     * Constructs a new instance.
     *
     * @param statusCode the HTTP status code
     * @param message the exception message
     * @param cause the root cause
     */
    public ServiceCallException(HttpStatusCode statusCode, String message, Throwable cause) {
        super(message, cause);

        httpStatusCode = statusCode;
    }
}
