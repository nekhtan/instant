package com.instant.fetcher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.instant.fetcher.exception.ServiceCallException;

import org.apache.hc.core5.net.URIBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpTimeoutException;

/**
 * Service to perform REST calls.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RestService {

    private final RestClient restClient;

    /**
     * Performs a GET request to the given path and maps the response to the given type.
     *
     * @param path the endpoint path
     * @param responseType the response type
     * @return the response entity
     * @param <T> the response type
     */
    @Retryable(retryFor = ServiceCallException.class, maxAttempts = 2)
    public <T> ResponseEntity<T> get(String path, Class<T> responseType) {
        URI uri = createEndpoint(path);

        log.info("Calling endpoint GET {}", uri);

        try {
            return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(responseType);
        }
        catch (HttpStatusCodeException e) {
            log.error("[{}] Error while calling GET {}: {} - {} - {}",
                e.getStatusCode().value(),
                path,
                e.getMessage(),
                e.getResponseHeaders() == null ? "[]" : e.getResponseHeaders().toString(),
                e.getResponseBodyAsString(),
                e);
            throw new ServiceCallException(e.getStatusCode(), e.getMessage(), e);
        }
        catch (ResourceAccessException e) {
            Throwable cause = e.getMostSpecificCause();
            if (cause instanceof SocketTimeoutException || cause instanceof HttpTimeoutException) {
                log.error("Timeout error while calling GET {}: {}", path, e.getMessage(), e);
                throw new ServiceCallException("Timeout error: " + path, e);
            }
            log.error("Error while calling GET {} : {} : {}", path, cause, e.getMessage(), e);
            throw new ServiceCallException("Unexpected error: " + path, e);
        }
    }

    private static URI createEndpoint(String path) {
        try {
            URIBuilder uriBuilder = new URIBuilder(path);
            return uriBuilder.build();
        }
        catch (URISyntaxException e) {
            log.error("Unable to create endpoint url = {}", path, e);
            throw new ServiceCallException("Unable to created endpoint (syntax error)", e);
        }
    }
}
